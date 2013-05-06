package net.minecraft.src.nbxlite.gui;

import net.minecraft.src.*;

public class PageBeta extends Page{
    private GuiButton[] featuresButtons;
    private GuiButton newOresButton;
    private GuiButton jungleButton;
    private GuiButton iceDesertButton;
    private GuiButton fixBeachesButton;
    private boolean newores;
    private boolean jungle;
    private boolean iceDesert;
    private boolean fixbeaches;
    private int features;

    public PageBeta(GuiNBXlite parent){
        super(parent);
        featuresButtons = new GuiButton[GeneratorList.feat1length + 1];
        jungle = ODNBXlite.getDefaultFlag("jungle");
        newores = ODNBXlite.getDefaultFlag("newores");
        iceDesert = ODNBXlite.getDefaultFlag("icedesert");
        fixbeaches = ODNBXlite.getDefaultFlag("fixbeaches");
        features = ODNBXlite.DefaultFeaturesBeta;
    }

    @Override
    public void initButtons(){
        int l = featuresButtons.length;
        for (int i = 0; i < l; i++){
            featuresButtons[i] = new GuiButton(i, (width / 2 - 115) + leftmargin, 0, 210, 20, "");
            String name = mod_OldDays.lang.get("nbxlite.betafeatures" + (i + 1));
            if (i != ODNBXlite.FEATURES_SKY){
                name += " (" + mod_OldDays.lang.get("nbxlite.betafeatures" + (i + 1) + ".desc") + ")";
            }
            featuresButtons[i].displayString = name;
            addButton(featuresButtons[i]);
        }
        addButton(newOresButton = new GuiButton(l, width / 2 - 85 + leftmargin, 0, 150, 20, ""));
        addButton(jungleButton = new GuiButton(l + 1, width / 2 - 85 + leftmargin, 0, 150, 20, ""));
        addButton(iceDesertButton = new GuiButton(l + 2, width / 2 - 85 + leftmargin, 0, 150, 20, ""));
        addButton(fixBeachesButton = new GuiButton(l + 3, width / 2 - 85 + leftmargin, 0, 150, 20, ""));
        featuresButtons[features].enabled=false;
    }

    @Override
    public void scrolled(){
        for (int i = 0; i < featuresButtons.length; i++){
            featuresButtons[i].yPosition = height / 6 + ((i - 1) * 21) + scrollingGui.scrolling;
        }
        newOresButton.yPosition = height / 6 + 127 + scrollingGui.scrolling;
        jungleButton.yPosition = height / 6 + 149 + scrollingGui.scrolling;
        iceDesertButton.yPosition = height / 6 + 171 + scrollingGui.scrolling;
        fixBeachesButton.yPosition = height / 6 + (jungleButton.drawButton ? 193 : 149) + scrollingGui.scrolling;
        updateButtonPosition();
    }

    @Override
    public int getContentHeight(){
        return jungleButton.drawButton ? 193 : (fixBeachesButton.drawButton ? 149 : 127);
    }

    @Override
    public void updateButtonText(){
        newOresButton.displayString = mod_OldDays.lang.get("flag.newores") + ": " + mod_OldDays.lang.get("gui." + (newores ? "on" : "off"));
        jungleButton.displayString = mod_OldDays.lang.get("flag.jungle") + ": " + mod_OldDays.lang.get("gui." + (jungle ? "on" : "off"));
        iceDesertButton.displayString = mod_OldDays.lang.get("flag.icedesert") + ": " + mod_OldDays.lang.get("gui." + (iceDesert ? "on" : "off"));
        fixBeachesButton.displayString = mod_OldDays.lang.get("flag.fixbeaches") + ": " + mod_OldDays.lang.get("gui." + (fixbeaches ? "on" : "off"));
    }

    @Override
    public void updateButtonVisibility(){
        jungleButton.drawButton = features == 5;
        iceDesertButton.drawButton = features == 5;
        fixBeachesButton.drawButton = features <= 5;
    }

    @Override
    public void actionPerformedScrolling(GuiButton guibutton){
        if (guibutton == newOresButton){
            newores = !newores;
        }else if (guibutton == jungleButton){
            jungle = !jungle;
        }else if (guibutton == iceDesertButton){
            iceDesert = !iceDesert;
        }else if (guibutton == fixBeachesButton){
            fixbeaches = !fixbeaches;
        }else if (guibutton.id < featuresButtons.length){
            featuresButtons[features].enabled = true;
            features = guibutton.id;
            guibutton.enabled = false;
        }
        updateButtonPosition();
        updateButtonVisibility();
        updateButtonText();
        calculateMinScrolling();
    }

    @Override
    public void applySettings(){
        ODNBXlite.Generator = ODNBXlite.GEN_OLDBIOMES;
        ODNBXlite.MapFeatures = features;
        ODNBXlite.setFlag("jungle", jungle);
        ODNBXlite.setFlag("icedesert", iceDesert);
        ODNBXlite.setFlag("newores", newores);
        ODNBXlite.setFlag("fixbeaches", fixbeaches);
    }

    @Override
    public void setDefaultSettings(){
        features = ODNBXlite.DefaultFeaturesBeta;
        newores = ODNBXlite.getDefaultFlag("newores");
        jungle = ODNBXlite.getDefaultFlag("jungle");
        iceDesert = ODNBXlite.getDefaultFlag("icedesert");
        fixbeaches = ODNBXlite.getDefaultFlag("fixbeaches");
    }

    @Override
    public void loadFromWorldInfo(WorldInfo w){
        features = w.mapGenExtra;
        newores = ODNBXlite.getFlagFromString(w.flags, "newores");
        jungle = ODNBXlite.getFlagFromString(w.flags, "jungle");
        iceDesert = ODNBXlite.getFlagFromString(w.flags, "icedesert");
        fixbeaches = ODNBXlite.getFlagFromString(w.flags, "fixbeaches");
    }

    @Override
    public String getString(){
        String str = mod_OldDays.lang.get("nbxlite.betafeatures" + (features + 1));
        if (jungle){
            str += " (" + mod_OldDays.lang.get("betaJungle") + ")";
        }
        return str;
    }
}