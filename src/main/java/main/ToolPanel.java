package main;

import main.views.Army;
import main.views.Cell.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.views.City;
import main.views.PlayersHandler;

import java.io.IOException;

/**
 * <p>Tool panel below map</p>
 *
 * @author Vasia_Pupkin
 * @version 0.0.0
 */
public final class ToolPanel extends Group {
    private static final String backgroundImagePath = "file:resources\\images\\toolbar\\Cobblestone.png";
    private static ToolPanel instance;
    private static int PANEL_WIDTH = 840;
    private static int PANEL_HEIGHT = 660 / 3 - 40;

    private GridPane miniMap = new GridPane();//maybe not map, just place for some shit
    private MainPanel mainPanel = new MainPanel();
    private ActionsPanel actionsPanel = new ActionsPanel();
    private static final GridPane tools = new GridPane();

    //getInstance

    private ToolPanel() {
        super();
        defaultPanelView();
    }

    public static ToolPanel getInstance() {
        if (instance == null)
            instance = new ToolPanel();
        return instance;
    }

    //getters & setters

    public static int getPanelWidth() {
        return PANEL_WIDTH;
    }

    public static void setPanelWidth(int panelWidth) {
        PANEL_WIDTH = panelWidth;
    }

    public static int getPanelHeight() {
        return PANEL_HEIGHT;
    }

    public static void setPanelHeight(int panelHeight) {
        PANEL_HEIGHT = panelHeight;
    }

    public ActionsPanel getActionsPanel() {
        return actionsPanel;
    }

    //methods

    public void refresh(Cell cell) {
        if ((cell.getClass()).equals(CityCell.class) & (cell).isChosen()) {
            //case, when we leave chosen city
            defaultPanelView();
        } else {
            // any other case
            setAppropriatePanelView(cell);
        }
    }

    private void setAppropriatePanelView(Cell cell) {
        mainPanel = new MainPanel(cell);
        actionsPanel = new ActionsPanel(cell);
        tools.add(mainPanel, 1, 0);
        tools.add(actionsPanel, 2, 0);
    }

    public void defaultPanelView() {
        boolean kostyl = true;
        if (this.getChildren().size() > 1) {
            kostyl = false;
        }
        this.getChildren().clear();
        GridPane cobblestoneCellsBackground = new GridPane();
        //background
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++) {
                Rectangle background = new Rectangle();
                background.setFill(new ImagePattern(new Image(backgroundImagePath)));
                background.setHeight(PANEL_HEIGHT / 3);
                background.setWidth(PANEL_WIDTH / 4);
                cobblestoneCellsBackground.add(background, x, y);
            }
        }
        ToolPanel.this.getChildren().add(cobblestoneCellsBackground);
        //other shit
        tools.setHgap(10);
        tools.setVgap(10);
        //    miniMap = new GridPane();
        mainPanel = new MainPanel();
        actionsPanel = new ActionsPanel();
        if (kostyl) {
            initMapPanel();
            //          tools.add(miniMap, 0, 0);
        }
        tools.add(mainPanel, 1, 0);
        tools.add(actionsPanel, 2, 0);
        this.getChildren().add(tools);
    }

    public void initMapPanel() {
        miniMap = new GridPane();
//        Rectangle miniMapBackground = new Rectangle();
//        miniMapBackground.setFill(Paint.valueOf("GRAY"));
//        miniMapBackground.setWidth(PANEL_WIDTH / 4);
//        miniMapBackground.setHeight(PANEL_HEIGHT);
        miniMap.setStyle("-fx-background-color: #808080;");
        MiniMap.getMiniMap(miniMap, PANEL_WIDTH / 4 - 15, PANEL_HEIGHT);
        //TODO be careful with this shit, Artem
        tools.add(miniMap, 0, 0);
    }

    /**
     * Main panel with cell's stats
     *
     * @author Vasia_Pupkin
     */
    class MainPanel extends GridPane {
        // linear dimensions
        private final int MAIN_PANEL_WIDTH = PANEL_WIDTH / 2 - 5;
        private final int MAIN_PANEL_HEIGHT = PANEL_HEIGHT;
        //icons for battle stats
        private final String pathToHeart = "file:resources//images//toolbar//stats//Heart.png";
        private final String pathToSword = "file:resources//images//toolbar//stats//Attack.png";
        private final String pathToShield = "file:resources//images//toolbar//stats//Defence.png";
        private final ImagePattern healthImage = new ImagePattern(new Image(pathToHeart));
        private final ImagePattern attackImage = new ImagePattern(new Image(pathToSword));
        private final ImagePattern defenseImage = new ImagePattern(new Image(pathToShield));
        //icons for resources stats
        private final String pathToGold = "file:resources//images//toolbar//resources//Gold.png";
        private final String pathToMinerals = "file:resources//images//toolbar//resources//Mineral.png";
        private final String pathToFood = "file:resources//images//toolbar//resources//Bread.png";
        private final ImagePattern goldImage = new ImagePattern(new Image(pathToGold));
        private final ImagePattern mineralImage = new ImagePattern(new Image(pathToMinerals));
        private final ImagePattern breadImage = new ImagePattern(new Image(pathToFood));

        // other important(no) shit
        private Label caption = new Label();
        private Label x = new Label("x:");
        private Label y = new Label("y:");
        private Label endMoveButton = new Label("Кінець ходу");
        //private Cell view;

        // constructors

        public MainPanel() {
            defaultView();
        }

        private void defaultView() {
            this.getChildren().clear();
            Rectangle miniMapBackground = new Rectangle();
            miniMapBackground.setFill(Paint.valueOf("GRAY"));
            miniMapBackground.setWidth(MAIN_PANEL_WIDTH);
            miniMapBackground.setHeight(MAIN_PANEL_HEIGHT);
            this.add(miniMapBackground, 0, 0, 8, 3);
            initCaption();
            this.add(caption, 0, 0, 8, 1);
            initEndMoveButton();
            initCoordinates();
            this.setHgap(5);
            this.setVgap(5);
        }

        // if some cell is chosen

        public MainPanel(Cell cell) {
            defaultView();
            initCaption(cell);
            initStats(cell);
            initCoordinates(cell);
        }

        // caption

        private void initCaption() {
            caption.setText("");
            caption.setPrefWidth(MAIN_PANEL_WIDTH);
            caption.setPrefHeight(MAIN_PANEL_HEIGHT / 5);
        }

        private void initCaption(Cell cell) {
            caption.setStyle("-fx-background-color: grey; -fx-text-fill: green; -fx-text-alignment: center");
            caption.setAlignment(Pos.CENTER);
            caption.setPrefWidth(MAIN_PANEL_WIDTH);
            caption.setPrefHeight(MAIN_PANEL_HEIGHT / 5);
            caption.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, 30));
            if (cell.getClass().equals(CityCell.class)) {
                cityCellCase();
            } else if (cell.getClass().equals(ArmyCell.class)) {
                armyCellCase();
            } else {
                emptyCellCase();
            }
        }

        private void cityCellCase() {
            this.caption.setText("City");
        }

        private void armyCellCase() {
            this.caption.setText("Army");
        }

        private void emptyCellCase() {
            this.caption.setText("Empty cell");
        }

        // stats

        private void initStats(Cell cell) {
            if (cell.getClass().equals(CityCell.class)) {
                cityCellStats(cell);
            } else if (cell.getClass().equals(ArmyCell.class)) {
                armyCellStats(cell);
            } else {
                emptyCellStats();
            }
        }

        private void emptyCellStats() {

        }

        private void armyCellStats(Cell cell) {
            try {
                if (!PlayersHandler.getPlayersHandler().getPlayer(0).hasArmy(((ArmyCell) cell).getArmy())) {
                    System.out.println("wrong army");
                    return;
                }
            } catch (Exception exception) {
                System.out.println("exception");
                return;
            }
            Rectangle health = new Rectangle();
            Rectangle attack = new Rectangle();
            Rectangle defence = new Rectangle();
            health.setFill(healthImage);
            attack.setFill(attackImage);
            defence.setFill(defenseImage);
            Rectangle[] images = {health, attack, defence};
            for (Rectangle i : images) {
                i.setWidth(40);
                i.setHeight(40);
            }
            Label healthInfo = new Label();
            Label defenceInfo = new Label();
            Label attackInfo = new Label();
            healthInfo.setText(String.valueOf(((ArmyCell) cell).getArmy().getHealth()));
            attackInfo.setText(String.valueOf(((ArmyCell) cell).getArmy().getAttackDamage()));
            defenceInfo.setText(String.valueOf(((ArmyCell) cell).getArmy().getDefenceDamage()));
            initLabel(healthInfo);
            initLabel(attackInfo);
            initLabel(defenceInfo);
            MainPanel.this.add(health, 2, 1);
            MainPanel.this.add(healthInfo, 3, 1);
            MainPanel.this.add(attack, 4, 1);
            MainPanel.this.add(attackInfo, 5, 1);
            MainPanel.this.add(defence, 6, 1);
            MainPanel.this.add(defenceInfo, 7, 1);
        }

        private void cityCellStats(Cell cell) {
            try {
                if (!PlayersHandler.getPlayersHandler().getPlayer(0).hasCity(((CityCell) cell).getCity())) {
                    System.out.println("wrong city");
                    return;
                }
            } catch (Exception exception) {
                return;
            }

            Rectangle health = new Rectangle();
            Rectangle defence = new Rectangle();
            health.setFill(healthImage);
            defence.setFill(defenseImage);
            Rectangle[] images = {health, defence};
            for (Rectangle i : images) {
                i.setWidth(40);
                i.setHeight(40);
            }
            //show stats
            //battle stats
            Label healthInfo = new Label();
            Label defenceInfo = new Label();
            healthInfo.setText(String.valueOf(((CityCell) cell).getCity().getHealth()));
            defenceInfo.setText(String.valueOf(((CityCell) cell).getCity().getDefenceDamage()));
            initLabel(healthInfo);
            initLabel(defenceInfo);
            MainPanel.this.add(health, 2, 1);
            MainPanel.this.add(healthInfo, 3, 1);
            MainPanel.this.add(defence, 4, 1);
            MainPanel.this.add(defenceInfo, 5, 1);
            //income and resources
            Rectangle gold = new Rectangle();
            Rectangle mineral = new Rectangle();
            Rectangle food = new Rectangle();
            Rectangle[] resources = {gold, mineral, food};
            for (Rectangle i : resources) {
                i.setWidth(40);
                i.setHeight(40);
            }
            gold.setFill(goldImage);
            mineral.setFill(mineralImage);
            food.setFill(breadImage);
            //income
            int goldIncome = -1;
            int mineralIncome = -1;
            int foodIncome = -1;
            for (BuildingCell i : ((CityCell) cell).getCity().getBuildings()) {
                if (i.getClass().equals(GoldmineCell.class)) {
                    goldIncome += 2;
                } else if (i.getClass().equals(MineralCell.class)) {
                    mineralIncome += 2;
                } else if (i.getClass().equals(FieldCell.class)) {
                    foodIncome += 2;
                }
            }
            //labels
            Label goldInfo = new Label(((CityCell) cell).getCity().getResGold() + "/" + goldIncome);
            Label mineralInfo = new Label(((CityCell) cell).getCity().getResMineral() + "/" + mineralIncome);
            Label foodInfo = new Label(((CityCell) cell).getCity().getResFood() + "/" + foodIncome);
            initLabel(goldInfo);
            initLabel(mineralInfo);
            initLabel(foodInfo);
            MainPanel.this.add(gold, 2, 2);
            MainPanel.this.add(goldInfo, 3, 2);
            MainPanel.this.add(mineral, 4, 2);
            MainPanel.this.add(mineralInfo, 5, 2);
            MainPanel.this.add(food, 6, 2);
            MainPanel.this.add(foodInfo, 7, 2);
        }

        //coordinates

        private void initCoordinates() {
            x.setFont(Font.font("Candara", FontWeight.BOLD, 20));
            y.setFont(Font.font("Candara", FontWeight.BOLD, 20));
            x.setStyle("-fx-background-color: #808080FF; -fx-text-fill: green; -fx-text-alignment: center");
            y.setStyle("-fx-background-color: #808080FF; -fx-text-fill: green; -fx-text-alignment: center");
            x.setText("x: ");
            y.setText("y: ");
            x.setPrefWidth(40);
            y.setPrefWidth(40);
            //rowindex 1 because I left place for background
            MainPanel.this.add(x, 0, 1);
            MainPanel.this.add(y, 1, 1);
        }

        private void initCoordinates(Cell cell) {
            x.setStyle("-fx-background-color: #808080FF; -fx-text-fill: #008000FF; -fx-text-alignment: center");
            y.setStyle("-fx-background-color: #808080FF; -fx-text-fill: #008000FF; -fx-text-alignment: center");
            x.setText("x:" + (cell.takeX() + 1));
            y.setText("y:" + (cell.takeY() + 1));
            //rowindex 1 because I left place for background
        }

        private void initEndMoveButton() {
            endMoveButton.setFont(Font.font("Candara", FontWeight.BOLD, 15));
            endMoveButton.setAlignment(Pos.CENTER);
            endMoveButton.setStyle("-fx-background-color: orange; -fx-text-fill: black; -fx-text-alignment: center");
            endMoveButton.setOnMouseEntered(e -> {
                endMoveButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-text-alignment: center");
            });
            endMoveButton.setOnMouseExited(e -> {
                endMoveButton.setStyle("-fx-background-color: orange; -fx-text-fill: black; -fx-text-alignment: center");
            });
            endMoveButton.setOnMouseClicked(e -> {
                try {
                    //TODO decide whether bot move 1 army, or all armies
                    //PlayersHandler.getPlayersHandler().getPlayer(1).moveAllBotArmies();
                    PlayersHandler.getPlayersHandler().getPlayer(1).moveArmy();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //right mechanics of collecting and spending resources
                for (City i : PlayersHandler.getPlayersHandler().getPlayer(0).getCities()) {
                    i.collectResources();
                    i.spendResources();
                }
                for (City i : PlayersHandler.getPlayersHandler().getPlayer(1).getCities()) {
                    i.collectResources();
                    i.spendResources();
                }
                for (City i : PlayersHandler.getPlayersHandler().getPlayer(0).getCities()) {
                    if (i.getCityCell().isChosen()) {
                        i.getCityCell().setChosen(false);
                        i.getCityCell().changeTerritoryHighlight();
                        i.getCityCell().setChosen(true);
                    }
                    for (Army j : i.getArmies()) {
                        if (j.getArmyCell().isChosen()) {
                            j.getArmyCell().fillFields();
                            j.getArmyCell().setChosen(true);
                        }
                    }
                }
            });
            endMoveButton.setPrefWidth(80);
            endMoveButton.setPrefHeight(30);
            this.add(endMoveButton, 0, 2, 2, 1);
        }

        private void initCellImage() {

        }

    }

    /**
     * Panel with possible actions for every chosen cell
     *
     * @author Vasia_Pupkin
     */
    public class ActionsPanel extends GridPane {
        // dimensions
        private final int ACTION_PANEL_WIDTH = PANEL_WIDTH / 4;
        private final int ACTION_PANEL_HEIGHT = PANEL_HEIGHT;
        // images of buttons
        private final Paint unenteredButton = Paint.valueOf("GREEN");
        private final Paint enteredButton = Paint.valueOf("BLUE");
        private final Paint chosenButton = Paint.valueOf("PINK");
        private final Paint inactiveElement = Paint.valueOf("GRAY");
        private final String pathToBuilding = "file:resources//images//toolbar//actions//Build.png";
        private final String pathToRecruiting = "file:resources//images//toolbar//actions//Recruit.png";
        private final String pathToNewArmy = "file:resources//images//toolbar//actions//NewArmy.png";
        private final String pathToUpgrade = "file:resources//images//toolbar//actions//Upgrade.png";
        private final String pathToGold = "file:resources//images//toolbar//resources//Gold.png";
        private final String pathToMineral = "file:resources//images//toolbar//resources//Mineral.png";
        private final String pathToField = "file:resources//images//toolbar//resources//Bread.png";
        private final String pathToDestruction = "file:resources//images//toolbar//actions//Destroy.png";
        private final String pathToHealth = "file:resources//images//toolbar//stats//Heart.png";
        private final String pathToAttack = "file:resources//images//toolbar//stats//Attack.png";
        private final String pathToDefence = "file:resources//images//toolbar//stats//Defence.png";
        private final String pathToExit = "file:resources//images//toolbar//actions//Exit.jpg";
        private final ImagePattern buildingImage = new ImagePattern(new Image(pathToBuilding));
        private final ImagePattern recruitingImage = new ImagePattern(new Image(pathToRecruiting));
        private final ImagePattern destructionImage = new ImagePattern(new Image(pathToDestruction));
        private final ImagePattern newArmyImage = new ImagePattern(new Image(pathToNewArmy));
        private final ImagePattern upgradeImage = new ImagePattern(new Image(pathToUpgrade));
        private final ImagePattern fieldImage = new ImagePattern(new Image(pathToField));
        private final ImagePattern goldmineImage = new ImagePattern(new Image(pathToGold));
        private final ImagePattern mineralMineImage = new ImagePattern(new Image(pathToMineral));
        private final ImagePattern healthImage = new ImagePattern(new Image(pathToHealth));
        private final ImagePattern attackImage = new ImagePattern(new Image(pathToAttack));
        private final ImagePattern defenceImage = new ImagePattern(new Image(pathToDefence));
        private final ImagePattern exitImage = new ImagePattern(new Image(pathToExit));

        // buttons
        private Rectangle[] options = new Rectangle[9];

        // other shit

        private Class<? extends Cell> buildingType = null;
        private boolean cityIsActivated = false;
        private boolean readyToDelete = false;

        //constructors

        public ActionsPanel() {
            setView();
        }

        public ActionsPanel(Cell cell) {
            setView(cell);
        }

        // initializing view of the panel

        /**
         * Default view of the panel
         */
        public void setView() {
            this.getChildren().clear();
            Rectangle miniMapBackground = new Rectangle();
            miniMapBackground.setFill(Paint.valueOf("GRAY"));
            miniMapBackground.setWidth(ACTION_PANEL_WIDTH);
            miniMapBackground.setHeight(ACTION_PANEL_HEIGHT);
            this.setHgap(3);
            this.setVgap(3);
            this.add(miniMapBackground, 0, 0, 3, 3);
            setOptionButtonsDefaultSettings();
        }

        private void setOptionButtonsDefaultSettings() {
            int n = 0;
            for (Rectangle i : options) {
                i = new Rectangle();
                i.setFill(inactiveElement);
                i.setStroke(inactiveElement);
                i.setWidth(ACTION_PANEL_WIDTH / 3 - 2);
                i.setHeight(ACTION_PANEL_HEIGHT / 3 - 2);
                this.add(i, n % 3, n / 3);
                setDefaultButtonSettings(i);
                options[n++] = i;
            }
        }

        /**
         * Sets view and options, appropriate to given cell
         */
        public void setView(Cell cell) {
            setView();
            if (cell.getClass().equals(CityCell.class) && PlayersHandler.getPlayersHandler().getPlayer(0).hasCity(((CityCell) cell).getCity())) {
                initCityCase((CityCell) cell);
            } else if (cell.getClass().equals(ArmyCell.class) && PlayersHandler.getPlayersHandler().getPlayer(0).hasArmy(((ArmyCell) cell).getArmy())) {
                armyAction((ArmyCell) cell);
            }
        }

        // cases for each type of cell

        private void initCityCase(CityCell cell) {
            setOptionButtonsDefaultSettings();
            options[0].setFill(buildingImage);
            options[1].setFill(recruitingImage);
            options[6].setFill(destructionImage);
            options[8].setFill(exitImage);
            options[0].setOnMouseClicked(e -> {
                buildAction(cell);
            });
            options[1].setOnMouseClicked(e -> recruitAction(cell));
            options[6].setOnMouseClicked(e -> destructionAction(cell));
            options[8].setOnMouseClicked(e -> {
                cell.setChosen(false);
                cityIsActivated = false;
                buildingType = null;
                cell.changeTerritoryHighlight();
                mainPanel.defaultView();
                setView();
            });
            setDefaultHighlight(options[0]);
            setDefaultHighlight(options[1]);
        }

        private void initArmyCellCase(ArmyCell cell) {

        }

        private void initEmptyCellCase(Cell cell) {
            //does nothing. Maybe will do smth useful in future
        }

        //getters & setters

        public Class<? extends Cell> getBuildingType() {
            return buildingType;
        }

        public boolean cityIsActivated() {
            return cityIsActivated;
        }

        public boolean isReadyToDelete() {
            return readyToDelete;
        }

        // methods

        // some idk, auxiliary shit

        // city options

        private void buildAction(CityCell cell) {
            setOptionButtonsDefaultSettings();
            inactiveButton(options[1]);
            inactiveButton(options[6]);
            buildingType = null;
            readyToDelete = false;
            cell.setChosen(true);
            cityIsActivated = true;

            cell.changeTerritoryHighlight();

            options[0].setFill(goldmineImage);
            options[1].setFill(mineralMineImage);
            options[2].setFill(fieldImage);
            setDefaultHighlight(options[2]);
            setDefaultHighlight(options[8]);
            options[8].setFill(exitImage);
            options[0].setOnMouseClicked(e -> {
                buildingType = GoldmineCell.class;
                optionIsChosen(options[0]);
                optionIsNotChosen(options[1]);
                optionIsNotChosen(options[2]);
            });
            options[1].setOnMouseClicked(e -> {
                buildingType = MineralCell.class;
                optionIsNotChosen(options[0]);
                optionIsChosen(options[1]);
                optionIsNotChosen(options[2]);
            });
            options[2].setOnMouseClicked(e -> {
                buildingType = FieldCell.class;
                optionIsNotChosen(options[0]);
                optionIsNotChosen(options[1]);
                optionIsChosen(options[2]);
            });

            options[8].setOnMouseClicked(e -> {
                cell.setChosen(false);
                cityIsActivated = false;
                buildingType = null;
                cell.changeTerritoryHighlight();
                setView();
                initCityCase(cell);
            });
        }

        private void recruitAction(CityCell cell) {
            cell.setChosen(true);
            cityIsActivated = true;
            readyToDelete = false;
            cell.changeTerritoryHighlight();
            optionIsChosen(options[1]);
            options[1].setFill(newArmyImage);
            options[1].setOnMouseClicked(e -> {
                cell.setChosen(false);
                readyToDelete = false;
                buildingType = null;
                cell.changeTerritoryHighlight();
                setView(cell);
                cell.setChosen(true);
                optionIsNotChosen(options[1]);
            });
            buildingType = ArmyCell.class;
        }

        private void destructionAction(CityCell cell) {
            cell.setChosen(true);
            cityIsActivated = true;
            readyToDelete = true;
            buildingType = MountainCell.class;
            optionIsChosen(options[6]);
            options[6].setOnMouseClicked(e -> {
                cell.setChosen(false);
                //cityIsActivated = false;
                readyToDelete = false;
                buildingType = null;
                cell.changeTerritoryHighlight();
                cell.setChosen(true);
                setView(cell);
            });
            cell.changeTerritoryHighlight();
        }

        // army options

        private void armyAction(ArmyCell cell) {
            setOptionButtonsDefaultSettings();
            options[0].setFill(upgradeImage);
            options[0].setOnMouseClicked(e -> {
                upgradeOptions(cell);
            });
        }

        private void upgradeOptions(ArmyCell cell) {
            setOptionButtonsDefaultSettings();
            options[0].setFill(healthImage);
            options[1].setFill(attackImage);
            options[2].setFill(defenceImage);
            options[8].setFill(exitImage);
            options[0].setOnMouseClicked(e -> {
                City city = cell.getArmy().getCity();
                Army army = cell.getArmy();
                if (!(city.getResGold() < 10 || city.getResMineral() < 10 || city.getResFood() < 15)) {
                    army.setHealth(cell.getArmy().getHealth() + 20);
                    city.setResGold(city.getResGold() - 10);
                    city.setResMineral(city.getResMineral() - 10);
                    city.setResFood(city.getResFood() - 15);
                }
                refresh(cell);
                //this.setView(cell);
            });
            options[1].setOnMouseClicked(e -> {
                City city = cell.getArmy().getCity();
                Army army = cell.getArmy();
                if (!(city.getResGold() < 10 || city.getResMineral() < 15 || city.getResFood() < 10)) {
                    army.setAttackDamage(cell.getArmy().getAttackDamage() + 7);
                    city.setResGold(city.getResGold() - 10);
                    city.setResMineral(city.getResMineral() - 15);
                    city.setResFood(city.getResFood() - 15);
                }
                refresh(cell);
                //this.setView(cell);
            });
            options[2].setOnMouseClicked(e -> {
                City city = cell.getArmy().getCity();
                Army army = cell.getArmy();
                if (!(city.getResGold() < 15 || city.getResMineral() < 10 || city.getResFood() < 10)) {
                    army.setDefenceDamage(cell.getArmy().getDefenceDamage() + 7);
                    city.setResGold(city.getResGold() - 15);
                    city.setResMineral(city.getResMineral() - 10);
                    city.setResFood(city.getResFood() - 15);
                }
                refresh(cell);
                //this.setView(cell);
            });
            options[8].setOnMouseClicked(e -> {
                setView(cell);
            });
        }

        private void setDefaultButtonSettings(Rectangle rectangle) {
            rectangle.setFill(inactiveElement);
            rectangle.setWidth(ACTION_PANEL_WIDTH / 3 - 5);
            rectangle.setHeight(ACTION_PANEL_HEIGHT / 3 - 6);
            rectangle.setStrokeWidth(1.0);
            rectangle.setStroke(inactiveElement);
            rectangle.setOnMouseEntered(e -> {
            });
            rectangle.setOnMouseExited(e -> {
                rectangle.setStroke(inactiveElement);
            });
        }

        private void setDefaultHighlight(Rectangle rectangle) {
            rectangle.setStroke(unenteredButton);
            rectangle.setOnMouseEntered(e -> rectangle.setStroke(enteredButton));
            rectangle.setOnMouseExited(e -> rectangle.setStroke(unenteredButton));
            rectangle.setStrokeWidth(3.0);
        }

        private void optionIsChosen(Rectangle rectangle) {
            rectangle.setStroke(chosenButton);
            rectangle.setOnMouseExited(e -> rectangle.setStroke(chosenButton));
            rectangle.setStrokeWidth(3.0);
        }

        private void optionIsNotChosen(Rectangle rectangle) {
            rectangle.setStroke(unenteredButton);
            rectangle.setOnMouseExited(e -> rectangle.setStroke(unenteredButton));
            rectangle.setStrokeWidth(1.0);
        }

        private void inactiveButton(Rectangle rectangle) {
            rectangle.setStrokeWidth(1.0);
            rectangle.setStroke(inactiveElement);
            rectangle.setOnMouseExited(e -> rectangle.setStroke(inactiveElement));
        }

    }

    // shit

    /**
     * Sets default settings for label in this particular panel
     */
    private void initLabel(Label label) {
        label.setFont(Font.font("Candara", FontWeight.BOLD, 30));
        label.setStyle("-fx-background-color: #808080FF; -fx-text-fill: green; -fx-text-alignment: center");
    }
}
