package com.pes.sokoban.Global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.pes.sokoban.Global.Game.levelsPlayed;

public class Package{
    public String fileName;
    public Boolean local;
    public String copyright;
    public String title;
    public String description;
    public String email;
    public String url;
    public Integer numLevels;
    public Array<Level> levels;

    public Package(String aFilename, Boolean aLocal){
        fileName = aFilename;
        local = aLocal;
        title = "no title info";
        description="no description info";
        email="no email info";
        url="no url info";
        numLevels = 0;
        levels = new Array<Level>();
    }

    public boolean load() {
        boolean loadOK = true;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;
            if (local) {
                doc = dBuilder.parse(Gdx.files.local(fileName).file());
            }
            else {
                doc = dBuilder.parse(Gdx.files.external(fileName).file());
            }
            Element rootElement = doc.getDocumentElement();
            if (rootElement.getElementsByTagName("Title").getLength()>0)
                title = rootElement.getElementsByTagName("Title").item(0).getTextContent();
            if (rootElement.getElementsByTagName("Description").getLength()>0)
                description = rootElement.getElementsByTagName("Description").item(0).getTextContent();
            if (rootElement.getElementsByTagName("Email").getLength()>0)
                email = rootElement.getElementsByTagName("Email").item(0).getTextContent();
            if (rootElement.getElementsByTagName("Url").getLength()>0)
                url=rootElement.getElementsByTagName("Url").item(0).getTextContent();
            if (rootElement.getElementsByTagName("LevelCollection").getLength()>0) {
                Element collectionElement = (Element) rootElement.getElementsByTagName("LevelCollection").item(0);
                copyright = collectionElement.getAttribute("Copyright");
            }

            NodeList nList = rootElement.getElementsByTagName("Level");
            numLevels = nList.getLength();
            if (numLevels > 0) {
                 for (int temp = 0; temp < nList.getLength(); temp++) {
                     Node nNode = nList.item(temp);
                     levels.add(new Level(this));
                     levels.get(temp).load(nNode);
                     levels.get(temp).removeArtefacts();
                     levels.get(temp).recalcWalls();
                     levels.get(temp).setToStart();
                     // levels.get(temp).createNiceThumbnail();
                     for (LevelPlayed levelPlayed : levelsPlayed) levels.get(temp).eventuallyAddInformation(levelPlayed);
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadOK = false;
        }
        return (loadOK);
    }

    public String getName(){
        int index = fileName.lastIndexOf("/");
        String ret = fileName.substring(index + 1);
        ret = ret.substring(0, ret.length()-4);

        return ret;
    }



    public String getRow(){
        String ret = "<Package Filename=\"" + fileName +
                "\" Local=\"" + local.toString() +
                "\">\n";
        return ret;
    }



    private String stringWrap(String aString, int aLenght) {
        String ret = "";
        int lenght = aString.length();
        int begin,end;
        begin = 0;
        while (begin<lenght-1) {
            end = begin + 30;
            if (end>lenght-1) end = lenght-1;
            ret = ret + aString.substring(begin, end)+" \n ";
            begin = end;
        }
        return ret;
    }


    public String getInfoText(int iWrap){
        String text = "";
        text = text + stringWrap(description,30) + "\n";
        text = text + "Email\n" + email + "\n";
        text = text + "URL\n" +  url;
        return text;
    }

}
