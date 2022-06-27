package com.pes.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.pes.sokoban.Constants.Constants;
import com.pes.sokoban.Global.Level;
import com.pes.sokoban.Global.LevelPlayed;
import com.pes.sokoban.Global.Package;
import com.pes.sokoban.Interfaces.Platform;
import com.pes.sokoban.Screens.InfoScreen;
import com.pes.sokoban.Screens.MenuScreen;
import com.pes.sokoban.Screens.PlayScreen;
import com.pes.sokoban.Screens.SplashScreen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.pes.sokoban.Global.Game.FULLVERSION;
import static com.pes.sokoban.Global.Game.activeLevel;
import static com.pes.sokoban.Global.Game.actualMIDI;
import static com.pes.sokoban.Global.Game.atlas;
import static com.pes.sokoban.Global.Game.dir;
import static com.pes.sokoban.Global.Game.language;
import static com.pes.sokoban.Global.Game.musicEnabled;
import static com.pes.sokoban.Global.Game.musicVolume;
import static com.pes.sokoban.Global.Game.replayStepIn;
import static com.pes.sokoban.Global.Game.screenAspectRatio;
import static com.pes.sokoban.Global.Game.screenOrientation;
import static com.pes.sokoban.Global.Game.soundEnabled;
import static com.pes.sokoban.Global.Game.soundVolume;
import static com.pes.sokoban.Global.Game.texThBox;
import static com.pes.sokoban.Global.Game.texThBoxOnTarget;
import static com.pes.sokoban.Global.Game.texThEmpty;
import static com.pes.sokoban.Global.Game.texThGround;
import static com.pes.sokoban.Global.Game.texThSokoban;
import static com.pes.sokoban.Global.Game.texThTarget;
import static com.pes.sokoban.Global.Game.texThWall;
import static com.pes.sokoban.Global.Game.textureAtlasCommon;
import static com.pes.sokoban.Global.Game.textureAtlasSkin;
import static com.pes.sokoban.Global.Game.versionCode;
import static com.pes.sokoban.Global.Game.virtualHeight;
import static com.pes.sokoban.Global.Game.virtualWidth;
import static com.pes.sokoban.Global.Game.portrait;
import static com.pes.sokoban.Global.Game.levelsPlayed;
import static com.pes.sokoban.Global.Game.levelPackages;
import static com.pes.sokoban.Global.Game.activePackage;
import static com.pes.sokoban.Global.Game.skin;
import static com.pes.sokoban.Global.Game.soundTap;
import static com.pes.sokoban.Global.Game.assetManager;

public class SokobanGame extends Game {
    public final Platform platform;

	private static long SPLASH_MINIMUM_MILLIS = 2000L;
	// screens
	private PlayScreen playScreen;
    private InfoScreen infoScreen;
	private MenuScreen menuScreen;
    // camera
	private OrthographicCamera camera;
	public FitViewport fitViewport;


	public SokobanGame(Platform platform){
		this.platform = platform;
    }

	@Override
	public void create() {
		setScreen(new SplashScreen());

		final long splash_start_time = System.currentTimeMillis();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
						setViewport();

						assetManager = new AssetManager();
						assetManager.load(Constants.S_SKIN, Skin.class);
						assetManager.load(Constants.S_ATLASCOMMON, TextureAtlas.class);
						assetManager.load(Constants.S_ATLASROBI, TextureAtlas.class);
						assetManager.load(Constants.S_ATLASBERUSKA, TextureAtlas.class);
						assetManager.load(Constants.S_ATLASRYBKA, TextureAtlas.class);
						assetManager.load(Constants.S_ATLASTUCI, TextureAtlas.class);

						assetManager.load(Constants.S_SOUNDSKIN, Sound.class);
						assetManager.load(Constants.S_SOUNDTAP, Sound.class);
						assetManager.load(Constants.S_SOUNDWALK, Sound.class);
						assetManager.load(Constants.S_SOUNDJUMP, Sound.class);
						assetManager.load(Constants.S_SOUNDPUSH, Sound.class);
						assetManager.load(Constants.S_SOUNDWIN, Sound.class);
						assetManager.load(Constants.S_SOUNDOPENWINDOW, Sound.class);
						assetManager.load(Constants.S_SOUNDDIALOG, Sound.class);
						assetManager.finishLoading();
						// TITLE STYLE

						skin = assetManager.get(Constants.S_SKIN);

						//
						Gdx.input.setCatchBackKey(true);

						soundTap = assetManager.get(Constants.S_SOUNDTAP, Sound.class);

						levelPackages = new Array<Package>();
						levelsPlayed = new Array<LevelPlayed>();

						load();
						getThTextures(); createThumbnails();

						// TEXTURE ATLAS
						textureAtlasCommon = assetManager.get(Constants.S_ATLASCOMMON, TextureAtlas.class);
						textureAtlasSkin = assetManager.get(atlas, TextureAtlas.class);

						activePackage = null;
						activeLevel = null;


						long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
						if (splash_elapsed_time < SokobanGame.SPLASH_MINIMUM_MILLIS) {
							Timer.schedule(
									new Timer.Task() {
										@Override
										public void run() {
											SokobanGame.this.setScreen(new MenuScreen(SokobanGame.this));
										}
									}, (float)(SokobanGame.SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
						} else {
//							platform.stop();
							SokobanGame.this.setScreen(new MenuScreen(SokobanGame.this));
					}
					}
				});
			}
		}).start();


	}

	public boolean setViewport(){
		boolean lastPortrait = portrait;
        float width = (float) Gdx.graphics.getWidth();
        float height = (float) Gdx.graphics.getHeight();
        screenAspectRatio = width / height;
        portrait = screenAspectRatio < 1;
        if (portrait) {
            virtualWidth = Constants.BASIC_SIZE;
            virtualHeight = Math.round(virtualWidth / screenAspectRatio);

        }
        else {
            virtualHeight = Constants.BASIC_SIZE;
            virtualWidth = Math.round(virtualHeight*screenAspectRatio);
        }
        fitViewport = new FitViewport(virtualWidth, virtualHeight, camera);
		return (lastPortrait != portrait);
    }

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		platform.stop(); platform.release();
		super.dispose();
		assetManager.dispose();
	}

	public void changeScreen(int screen) {
		switch (screen) {
			case Constants.PLAY:
			    if (playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case Constants.MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case Constants.INFO:
				if (infoScreen == null) infoScreen = new InfoScreen(this);
				this.setScreen(infoScreen);
				break;

		}
	}

    public boolean notSlcFile(FileHandle aFile){
	    int numLevels = 0;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Gdx.files.external(aFile.path()).file());
            Element rootElement = doc.getDocumentElement();
            NodeList nList = rootElement.getElementsByTagName("Level");
            numLevels = nList.getLength();
        } catch (Exception e) {
            // Todo dialog Error reading SLC file
            e.printStackTrace();
        }
	    return (numLevels==0);
    }

    public boolean packageInPackages(FileHandle aFile) {
		boolean newFile = true;
		for (Package pckg : levelPackages) {
			if (pckg.fileName.equals(aFile.toString())) newFile = false;
		}
		return !newFile;
	}


	public void updateLevelsPlayed(Level aLevel){
		for (LevelPlayed levelPlayed: levelsPlayed)
			if (levelPlayed.isEqual(aLevel))
				levelPlayed.updateLevelPlayed(aLevel);
	}

	public void addLevelToPlayed(Level aLevel){
		boolean isntInPlayed = true;
		int i=0;
//		i = 0;
		while (i<levelsPlayed.size && isntInPlayed) {
			if (levelsPlayed.get(i).isEqual(aLevel)) isntInPlayed = false;
			i++;
		}
		if (isntInPlayed) levelsPlayed.add(new LevelPlayed(aLevel));
	}

	private void load() {
		if (!Gdx.files.local(Constants.S_PLAYEDNAME).exists()) initialCopyInternalSLCFiles();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(Gdx.files.local(Constants.S_PLAYEDNAME).file());

			Element rootElement = doc.getDocumentElement();
			if (rootElement.getElementsByTagName("Music").getLength()>0)
				musicEnabled = Boolean.parseBoolean(rootElement.getElementsByTagName("Music").item(0).getTextContent());
			if (rootElement.getElementsByTagName("MusicVolume").getLength()>0)
				musicVolume = Float.parseFloat(rootElement.getElementsByTagName("MusicVolume").item(0).getTextContent());
			if (rootElement.getElementsByTagName("MusicID").getLength()>0)
				actualMIDI = Integer.parseInt(rootElement.getElementsByTagName("MusicID").item(0).getTextContent());
			if (rootElement.getElementsByTagName("Sound").getLength()>0)
				soundEnabled = Boolean.parseBoolean(rootElement.getElementsByTagName("Sound").item(0).getTextContent());
			if (rootElement.getElementsByTagName("SoundVolume").getLength()>0)
				soundVolume = Float.parseFloat(rootElement.getElementsByTagName("SoundVolume").item(0).getTextContent());
			if (rootElement.getElementsByTagName("ReplayStepIn").getLength()>0)
				replayStepIn = Float.parseFloat(rootElement.getElementsByTagName("ReplayStepIn").item(0).getTextContent());
			if (rootElement.getElementsByTagName("Orientation").getLength()>0)
				screenOrientation = rootElement.getElementsByTagName("Orientation").item(0).getTextContent();
			if (rootElement.getElementsByTagName("Atlas").getLength()>0)
				atlas = rootElement.getElementsByTagName("Atlas").item(0).getTextContent();
			if (rootElement.getElementsByTagName("Dir").getLength()>0)
				dir = rootElement.getElementsByTagName("Dir").item(0).getTextContent();
			if (rootElement.getElementsByTagName("Language").getLength()>0)
				language = Integer.parseInt(rootElement.getElementsByTagName("Language").item(0).getTextContent());
			if (rootElement.getElementsByTagName("Version").getLength()>0) {
				versionCode = Integer.parseInt(rootElement.getElementsByTagName("Version").item(0).getTextContent());
				if (versionCode==201903) FULLVERSION = true;
			}
			platform.getPurchased();

			NodeList nList = doc.getDocumentElement().getElementsByTagName("Level");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String title = eElement.getAttribute("Title");
					String copyright = eElement.getAttribute("Copyright");
					String idString = eElement.getAttribute("Id");
					int width = Integer.parseInt(eElement.getAttribute("Width"));
					int height = Integer.parseInt(eElement.getAttribute("Height"));
					Boolean solved = Boolean.parseBoolean(eElement.getAttribute("Solved"));
					String solvedMoves = eElement.getAttribute("SolvedMoves");
					Integer solvedTime = Integer.parseInt(eElement.getAttribute("SolvedTime"));
					String moves = eElement.getAttribute("Moves");
					Integer time = Integer.parseInt(eElement.getAttribute("Time"));
					levelsPlayed.add(new LevelPlayed(title,copyright,idString, width, height, solved, solvedMoves, solvedTime, moves, time));
				}
			}


			NodeList pList = doc.getDocumentElement().getElementsByTagName("Package");
			for (int temp = 0; temp < pList.getLength(); temp++) {
				Node pNode = pList.item(temp);
				if (pNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) pNode;
					String filename = eElement.getAttribute("Filename");
					Boolean local = Boolean.parseBoolean(eElement.getAttribute("Local"));
					levelPackages.add(new Package (filename, local));
					levelPackages.get(temp).load();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialCopyInternalSLCFiles(){
		FileHandle filePlayed = Gdx.files.internal(Constants.S_PLAYEDNAME);
		filePlayed.copyTo(Gdx.files.local(Constants.S_PLAYEDNAME));

		FileHandle[] files = Gdx.files.internal(Constants.S_SLCDIRECTORY).list();
		for(FileHandle file: files) {
			if (!Gdx.files.local(Constants.S_SLCDIRECTORY + file.name() + ".slc").exists()){
				file.copyTo(Gdx.files.local(file.path()));
			}
		}
	}

	public void save() {
		FileHandle file = Gdx.files.local(Constants.S_PLAYEDNAME);
		file.writeString("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n", false);
		file.writeString("<Settings>\n", true);

		file.writeString("<Music>"+musicEnabled.toString()+"</Music>\n", true);
		file.writeString("<MusicVolume>"+musicVolume.toString()+"</MusicVolume>\n", true);
		file.writeString("<MusicID>"+actualMIDI.toString()+"</MusicID>\n", true);
		file.writeString("<Sound>"+soundEnabled.toString()+"</Sound>\n", true);
		file.writeString("<SoundVolume>"+soundVolume.toString()+"</SoundVolume>\n", true);
		file.writeString("<ReplayStepIn>"+replayStepIn.toString()+"</ReplayStepIn>\n", true);
		file.writeString("<Orientation>"+screenOrientation+"</Orientation>\n", true);
		file.writeString("<Atlas>"+atlas+"</Atlas>\n", true);
		file.writeString("<Dir>"+dir+"</Dir>\n", true);
		file.writeString("<Language>"+language.toString()+"</Language>\n", true);
		file.writeString("<Version>"+versionCode.toString()+"</Version>\n", true);

		for (LevelPlayed levelHistory : levelsPlayed) {
			file.writeString(levelHistory.getRow(), true);
			file.writeString("</Level>\n", true);
		}
		for (Package pckg : levelPackages) {
			file.writeString(pckg.getRow(), true);
			file.writeString("</Package>\n", true);
		}

		file.writeString("</Settings>\n", true);
	}

	public void createThumbnails(){
		for (int i = 0; i < levelPackages.size; i++) {
			for (int j = 0; j < levelPackages.get(i).levels.size; j++) {
				levelPackages.get(i).levels.get(j).createNiceThumbnail();
			}
		}
	}

	public void getThTextures(){
		if (texThBox!=null) texThBox.dispose();
		texThBox = new Texture(dir+"thBox.png");
		if (texThWall!=null) texThWall.dispose();
		texThWall = new Texture(dir+"thWall.png");
		if (texThGround!=null) texThGround.dispose();
		texThGround = new Texture(dir+"thGround.png");
		if (texThBoxOnTarget!=null) texThBoxOnTarget.dispose();
		texThBoxOnTarget = new Texture(dir+"thBoxOnTarget.png");
		if (texThTarget!=null) texThTarget.dispose();
		texThTarget = new Texture(dir+"thTarget.png");
		if (texThEmpty!=null) texThEmpty.dispose();
		texThEmpty = new Texture(dir+"thEmpty.png");
		if (texThSokoban!=null) texThSokoban.dispose();
		texThSokoban = new Texture(dir+"thSokoban.png");

	}

	public String convertPortrait2Landscape(String aString){
		String sReturn = "";
		char sTemp[] = aString.toCharArray();
		for (int i=0; i<aString.length()-1; i++) {
			sReturn = sReturn + sTemp[i] + "\n";
		}
		sReturn = sReturn + sTemp[aString.length()-1];
		return sReturn;
	}


	}
