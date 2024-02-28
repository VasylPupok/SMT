package main.views;


import main.views.Cell.ArmyCell;

public class Army {

    private int health;
    private int attackDamage;
    private int defenceDamage;
    private ArmyCell armyCell;
    private City city;

    public Army() {
        health = 100;
        attackDamage = 20;
        defenceDamage = 15;
    }

    public ArmyCell getArmyCell() {
        return armyCell;
    }

    public void setArmyCell(ArmyCell armyCell) {
        this.armyCell = armyCell;
    }

    // Getters and setters

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health <= 0) {
            throw new IllegalArgumentException("Health of the army cannot be less or equal 0!");
        }
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        if (attackDamage <= 0) {
            throw new IllegalArgumentException("Damage of the army cannot be less or equal 0!");
        }
        this.attackDamage = attackDamage;
    }

    public int getDefenceDamage() {
        return defenceDamage;
    }

    public void setDefenceDamage(int defenceDamage) {
        this.defenceDamage = defenceDamage;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    // Other voids

    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }
}