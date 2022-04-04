package classDiagram;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A class of the class diagram
 * @author Marek Dohnal (xdohna48)
 * @since 2022-03-26
 */
public class CDClass {
    private String name;
    private int parent;
    private ArrayList<CDField> fields;
    private ArrayList<CDField> methods;
    private boolean isInterface;
    private HashMap<AnchorType, Point> anchors;
    private Point position;
    private int width;
    private int height;

    /**
     * Constructs an empty class with a name = "defaultName",
     * the class is not an interface, and has no parent.
     * Class has position (0,0), width = 10, height = 10.
     */
    public CDClass() {
        this.name = "defaultName";
        this.isInterface = false;
        this.fields = new ArrayList<CDField>();
        this.methods = new ArrayList<CDField>();
        this.parent = -1;
        this.position = new Point(0,0);
        this.width = 10;
        this.height = 10;
        this.anchors = new HashMap<>();
        setAnchors();
    }

    /**
     * Constructs a filled class
     * @param name the name of the class
     * @param parent the index od the parent class (superclass) in a class diagram
     * @param fields the fields, which the class contains
     * @param methods the class methods
     * @param isInterface true if the class in an interface, false otherwise
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner
     * @param width the width of the class
     * @param height the height of the class
     */
    public CDClass(String name, int parent,
                   ArrayList<CDField> fields, ArrayList<CDField> methods, boolean isInterface,
                   int x, int y, int width, int height) {
        this.name = name;
        this.parent = parent;
        this.isInterface = isInterface;
        this.fields = fields;
        this.methods = methods;
        this.position = new Point(x, y);
        this.width = width;
        this.height = height;
        this.anchors = new HashMap<>();
        setAnchors();
    }

    /**
     * Sets the name of the class
     * @param name the name of the class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the index of a parent class in a class diagram
     * @param parentIdx the index of the parent
     */
    public void setParent(int parentIdx) {
        this.parent = parentIdx;
    }

    /**
     * Sets the parent class to an index specified by a class in a class diagram.
     * @param cd the class diagram which contains the parent
     * @param parent the parent class
     */
    public void setParent(ClassDiagram cd, CDClass parent) {
        for (int i = 0; i < cd.classesLen(); i++) {
            if (parent == cd.getCDClass(i)) {
                this.parent = i;
                return;
            }
        }
        this.parent = -1;

    }

    /**
     * Removes the parent from the class (sets the index to -1).
     */
    public void removeParent() {
        this.parent = -1;
    }

    /**
     * Sets the fields of the class
     * @param fields the fields of the class
     */
    public void setFields(CDField[] fields) {
        this.fields = new ArrayList<>(Arrays.asList(fields));
    }

    /**
     * Sets the methods of the class
     * @param methods the methods of the class
     */
    public void setMethods(CDField[] methods) {
        this.methods = new ArrayList<>(Arrays.asList(methods));
    }

    /**
     * Adds a single field to the class
     * @param field the field to be added
     * @return true if field was added, false otherwise
     */
    public boolean addField(CDField field) {
        return this.fields.add(field);
    }

    /**
     * Adds a single method to the class
     * @param method the method to be added
     * @return true if method was added, false otherwise
     */
    public boolean addMethod(CDField method) {
        return this.methods.add(method);
    }

    /**
     * Sets the isInterface property
     * @param isInterface true if class is an interface, false otherwise
     */
    public void setInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    /**
     * Sets the position to x and y coordinates.
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner
     */
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
        setAnchors();
    }

    public void setWidth(int width) {
        this.width = width;
        setAnchors();
    }

    public void setHeight(int height) {
        this.height = height;
        setAnchors();
    }

    private void setAnchors() {
        Point upPoint = new Point(
                this.position.x + this.width/2,
                this.position.y
        );
        Point rightPoint = new Point(
                this.position.x + this.width,
                this.position.y + this.height/2
        );
        Point downPoint = new Point(
                this.position.x + this.width/2,
                this.position.y + this.height
        );
        Point leftPoint = new Point(
                this.position.x,
                this.position.y + this.height/2
        );
        this.anchors.put(AnchorType.UP, upPoint);
        this.anchors.put(AnchorType.RIGHT, rightPoint);
        this.anchors.put(AnchorType.DOWN, downPoint);
        this.anchors.put(AnchorType.LEFT, leftPoint);
    }
    /**
     *
     * @return the name of the class
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return the parent of the class
     */
    public int getParent() {
        return this.parent;
    }

    /**
     * Returns the parent of the class as an index
     * of the parent in the class diagram.
     * @param cd the class diagram containing the parent
     * @return index specifying the parent, or -1 if parent couldn't be found
     */
    public CDClass getParentAsCDClass(ClassDiagram cd) {
        if (this.parent != -1) {
            return cd.getCDClass(this.parent);
        }
        return null;
    }

    /**
     * @return the methods overriden in the subclass
     */
    public ArrayList<CDField> getOverridenMethods(ClassDiagram cd) {
        ArrayList<CDField> overridenMethods = new ArrayList<>();
        if (this.parent != -1) {
            CDClass parentClass = cd.getCDClass(this.parent);
            for (CDField method : this.methods) {
                if (parentClass.methods.contains(method) &&
                        method.getVisibility() != Visibility.PRIVATE) {
                    overridenMethods.add(method);
                }
            }
            return overridenMethods;
        }
        return null;
    }

    /**
     *
     * @return the fields of the class
     */
    public ArrayList<CDField> getFields() {
        return this.fields;
    }

    /**
     *
     * @return the methods of the class
     */
    public ArrayList<CDField> getMethods() {
        return this.methods;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getXposition() {
        return this.position.x;
    }

    public int getYposition() {
        return this.position.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean getInterface() {
        return this.isInterface;
    }
    /**
     * Returns the anchors as a HashMap
     * @return the HashMap of anchors
     */
    public HashMap<AnchorType, Point> getAnchors() {
        return this.anchors;
    }

    /**
     * Returns the point of an anchor as specified by its type.
     * @param anchorType The type of an anchor
     * @return The coordinates at which the anchor lies.
     */
    public Point getAnchorByKey(AnchorType anchorType) {
        return this.anchors.get(anchorType);
    }
}
