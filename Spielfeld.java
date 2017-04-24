package org.adermake.ecoGame2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGActorCollisionListener;
import ch.aplu.jgamegrid.GGMouse;
import ch.aplu.jgamegrid.GGMouseListener;
import ch.aplu.jgamegrid.GGMouseTouchListener;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

public class Spielfeld extends GameGrid implements GGMouseListener, GGMouseTouchListener, GGActorCollisionListener
{
  // LISTEN
  private ArrayList<Spezies> allSpezies = new ArrayList<Spezies>();

  //
  // Skin -- Health -- Strength -- Rps -- Stamina -- Hunger --

  private Actor button_addspez = new Actor("sprites/addspezies.png");

  // SPIELFELD IN INITIALISIERUNG

  public Spielfeld()
  {
    super(900, 600, 1, "sprites/spielfeld.png");
    // addMouseListener(this, GGMouse.lPress | GGMouse.lDrag );
    // Initialsierung der Knöpfe
    setTitle("Simulation");

    // button_addspezies.addButtonListener(this);
    GGMouse m = GGMouse.create();
    button_addspez.addMouseTouchListener(this, GGMouse.lPress | GGMouse.lRelease);
    addMouseListener(this, GGMouse.lDrag);
    addActor(button_addspez, new Location(50, 50));
  }

  // SPIELSCHLEIFE

  @Override
  public void act()
  {

    Set<String> takenCoordinates = new HashSet<String>();
    for (Actor actor : getActors(Spezies.class))
    {
      String coords = actor.getX() + " x " + actor.getY();
      if (takenCoordinates.contains(coords))
      {
        ((Spezies) actor).die();
        System.out.println("Collision at " + actor.getX() + " x " + actor.getY());
      } else
        takenCoordinates.add(coords);
    }

    for (Actor actor : getActors(Spezies.class))
    {
      // MOVE
      if (actor.getX() >= 890)
        actor.setDirection(180);
      if (actor.getX() <= 110)
        actor.setDirection(0);
      if (actor.getY() >= 590)
        actor.setDirection(270);
      if (actor.getY() <= 10)
        actor.setDirection(90);
      actor.setDirection(actor.getDirection() + Math.random() * 10 - Math.random() * 10);
      actor.move();
      // DECAY

      Spezies s = (Spezies) actor;
      if (!s.live())
      {
        removeActor(actor);
        System.out.println("actor died => removed " + actor);
      }
    }

  }

  private boolean doCollide(Actor actor1, Actor actor2)
  {
    return actor1 != actor2 && actor1.getX() == actor2.getX() && actor1.getY() == actor2.getY();
  }

  public boolean mouseEvent(GGMouse mouse)
  {

    Location location = toLocationInGrid(mouse.getX(), mouse.getY());
    if (mouse.getEvent() == GGMouse.lPress)
    {

      setTitle("CLICK");
      refresh();

    }
    return false;

  }

  public void mouseTouched(Actor actor, GGMouse mouse, Point spot)
  {
    if (mouse.getEvent() == GGMouse.lPress)
    {

      JFrame frame = new JFrame("Frame");

      String name = JOptionPane.showInputDialog(frame, "Name der Spezies [String]", "bam");
      String[] skins = { "Zergling", "Schleim", "Giraffe" };

      String skin = (String) JOptionPane.showInputDialog(frame, "Wähle das aussehen von" + name, "---",
          JOptionPane.QUESTION_MESSAGE, null, skins, skins[0]);

      String health = JOptionPane.showInputDialog(frame, "Zähigkeit von " + name + " [int]", "50");
      String strenght = JOptionPane.showInputDialog(frame, "Stärke von " + name + " [int]", "50");
      String rps = JOptionPane.showInputDialog(frame, "Reproduktionsrate von " + name + " [int]", "25");
      String hunger = JOptionPane.showInputDialog(frame, "Ausdauer von " + name + " [int]", "500000");

      String number = JOptionPane.showInputDialog(frame, "Wieviel von " + name + " wollen sie erschaffen? [int]", "20");

      int n = parseAsInteger(number);
      int a = parseAsInteger(health);
      int d = parseAsInteger(strenght);
      int e = parseAsInteger(rps);
      int r = 2;
      int s = parseAsInteger(hunger);

      // Skin -- Health -- Strength -- Rps -- Stamina -- Hunger --
      for (int i = n; i > 0; i--)
      {
        Spezies spezies = new Spezies("SPEZIES_" + skin, a, d, e, r, s);
        // allSpezies.add(spezies);
        // spezies.setDirection(Math.random() * 3600);
        // spezies.addActorCollisionListener(this);
        addActor(spezies, new Location(200, 200));
      }
      /*
       * System.out.println("1");
       * 
       * for (Spezies one : allSpezies) { for (Spezies another : allSpezies) {
       * if (one != another) one.addCollisionActor(another); } }
       * 
       * System.out.println("2"); for (Spezies one : allSpezies) { if
       * (!one.isAdded()) {
       * 
       * // addActor(one, new //
       * Location(110+(int)(Math.random()*8900),(int)(Math.random()*6000)));
       * addActor(one, new Location(200, 200)); one.setAdded(); } }
       * System.out.println("3");
       */

    }
  }

  private Integer parseAsInteger(String number)
  {
    int parsed = 0;

    try
    {
      parsed = Integer.valueOf(number);
    } catch (NumberFormatException nfe)
    {
      parsed = 10;
    }
    return parsed;
  }

  public int collide(Actor a1, Actor a2)
  {
    System.out.println("collision detected between " + a1 + " and " + a2);
    if (a1 instanceof Spezies)
    {
      if (a2 instanceof Spezies)
      {
        Spezies spezi1 = (Spezies) a1;
        Spezies spezi2 = (Spezies) a2;
        if (spezi2.isAlive())
          Interaction.fight(spezi1, spezi2);
      }
    }

    return 0;
  }
}
