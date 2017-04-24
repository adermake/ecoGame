import javax.swing.*;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.ArrayList;

public class Spielfeld extends GameGrid implements GGMouseListener, GGMouseTouchListener, GGActorCollisionListener {
    // LISTEN
    private ArrayList<Spezies> allSpezies = new ArrayList<Spezies>();


    //
    //                                   Skin -- Health -- Strength -- Rps -- Stamina -- Hunger -- 

    private Actor button_addspez = new Actor( efer "sprites/addspezies.png");


    //SPIELFELD IN INITIALISIERUNG

    public Spielfeld() {
        super(900, 600, 1, "sprites/spielfeld.png");
        //addMouseListener(this, GGMouse.lPress | GGMouse.lDrag );
        //Initialsierung der Knöpfe
        setTitle("Simulation");


        //button_addspezies.addButtonListener(this);
        GGMouse m = GGMouse.create();
        button_addspez.addMouseTouchListener(this, GGMouse.lPress | GGMouse.lRelease);
        addMouseListener(this, GGMouse.lDrag);
        addActor(button_addspez, new Location(50, 50));


        //Initialsierung der Spezies

        show();
        doRun();


    }


    // SPIELSCHLEIFE

    public void act() {
       
       /* plant.setX(plant.getX()+5);
        plant.setY(plant.getY()+5); */
        for (Spezies all : allSpezies) {
            //MOVE
            if (all.getX() >= 890)
                all.setDirection(180);
            if (all.getX() <= 110)
                all.setDirection(0);
            if (all.getY() >= 590)
                all.setDirection(270);
            if (all.getY() <= 10)
                all.setDirection(90);
            all.setDirection(all.getDirection() + Math.random() * 10 - Math.random() * 10);
            all.move();
            //DECAY


            if (!all.live()) {
                removeActor(all);
            }


        }

    }


    public boolean mouseEvent(GGMouse mouse) {

        Location location = toLocationInGrid(mouse.getX(), mouse.getY());
        if (mouse.getEvent() == GGMouse.lPress) {

            setTitle("CLICK");
            refresh();

        }
        return false;

    }

    public void mouseTouched(Actor actor, GGMouse mouse, Point spot) {
        if (mouse.getEvent() == GGMouse.lPress) {


            JFrame frame = new JFrame("Frame");

            String name = JOptionPane.showInputDialog(frame, "Name der Spezies [String]");
            String[] skins = {"Zergling", "Schleim", "Giraffe"};

            String skin = (String) JOptionPane.showInputDialog(frame,
                    "Wähle das aussehen von" + name,
                    "---",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    skins,
                    skins[0]);


            String health = JOptionPane.showInputDialog(frame, "Zähigkeit von " + name + " [int]");
            String strenght = JOptionPane.showInputDialog(frame, "Stärke von " + name + " [int]");
            String rps = JOptionPane.showInputDialog(frame, "Repruduktionsrate von " + name + " [int]");
            String hunger = JOptionPane.showInputDialog(frame, "Ausdauer von " + name + " [int]");

            String number = JOptionPane.showInputDialog(frame, "Wieviel von " + name + " wollen sie erschaffen? [int]");

            int n = Integer.valueOf(number);
            int a = Integer.valueOf(health);
            int d = Integer.valueOf(strenght);
            int e = Integer.valueOf(rps);
            int r = 2;
            int s = Integer.valueOf(hunger);

            //   Skin -- Health -- Strength -- Rps -- Stamina -- Hunger --
            for (int i = n; i > 0; i--) {
                Spezies spezies = new Spezies("SPEZIES_" + skin, a, d, e, r, s);
                allSpezies.add(spezies);
                spezies.setDirection(Math.random() * 3600);
                spezies.addActorCollisionListener(this);

            }

            System.out.println("1");

            for (Spezies one : allSpezies) {
                for (Spezies another : allSpezies) {
                    if (one != another)
                        one.addCollisionActor(another);
                }
            }

            System.out.println("2");
            for (Spezies one : allSpezies) {
                if (!one.isAdded()) {

                    //addActor(one, new Location(110+(int)(Math.random()*8900),(int)(Math.random()*6000)));
                    addActor(one, new Location(200, 200));
                    one.setAdded();
                }
            }
            System.out.println("3");
        }
    }


    public int collide(Actor a1, Actor a2) {
        System.out.println("collision detected between " + a1 + " and " + a2);
        if (a1 instanceof Spezies) {
            if (a2 instanceof Spezies) {
                Spezies spezi1 = (Spezies) a1;
                Spezies spezi2 = (Spezies) a2;
                if (spezi2.isAlive())
                    Interaction.fight(spezi1, spezi2);
            }
        }


        return 0;
    }
}
