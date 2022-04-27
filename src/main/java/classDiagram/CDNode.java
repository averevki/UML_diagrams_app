package classDiagram;

import sequenceDiagram.SDMessage;
import sequenceDiagram.SDObject;
import sequenceDiagram.SequenceDiagram;

/**
 * A node of a class diagram
 * @author Marek Dohnal (xdohna48)
 * @since 2022-03-26
 */
public class CDNode {
    private CDClass from;
    private AnchorType fAnchor;
    private AnchorType tAnchor;
    private CDClass to;
    private String fCard;
    private String tCard;
    private NodeType type;


    /**
     * Constructs a filled node
     * @param from The class from which the node originates
     * @param fAnchor The anchor of the originating class to which
     *                the node is connected
     * @param to The class at which the node ends
     * @param tAnchor The anchor of the terminating class to which
     *                the node is connected
     * @param fCard The cardinality at the originating class
     * @param tCard The cardinality at the terminating class
     * @param type The type of the node
     */
    public CDNode(CDClass from, AnchorType fAnchor, CDClass to, AnchorType tAnchor, String fCard,
                  String tCard, NodeType type) {
        this.from = from;
        this.fAnchor = fAnchor;
        this.to = to;
        this.tAnchor = tAnchor;
        this.fCard = fCard;
        this.tCard = tCard;
        this.type = type;
    }

    public void setfAnchor(AnchorType fAnchor) {
        this.fAnchor = fAnchor;
    }

    public void settAnchor(AnchorType tAnchor) {
        this.tAnchor = tAnchor;
    }

    public void setfCard(String fCard) {
        this.fCard = fCard;
    }

    public void setFrom(CDClass from, ClassDiagram cd) {
        this.from = from;
        for (SequenceDiagram sd : cd.getSequenceDiagrams()) {
            for (SDMessage msg : sd.getMessages()) {
                msg.setMarkedInconsistent(cd);
            }
        }
    }

    public void settCard(String tCard) {
        this.tCard = tCard;
    }

    public void setTo(CDClass to, ClassDiagram cd) {
        this.to = to;
        for (SequenceDiagram sd : cd.getSequenceDiagrams()) {
            for (SDMessage msg : sd.getMessages()) {
                msg.setMarkedInconsistent(cd);
            }
        }
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    /**
     *
     * @return the class from which the node originates
     */
    public CDClass getFrom() {
        return this.from;
    }

    /**
     *
     * @return the class at which the node ends
     */
    public CDClass getTo() {
        return this.to;
    }

    /**
     * Returns the originating class as an index in a class diagram,
     * which contains such class
     * @param cd the class diagram containing the class
     * @return the index of the class, or -1 if the class was not found
     */
    public int getFromAsInt(ClassDiagram cd) {
        for (int i = 0; i < cd.classesLen(); i++) {
            if (cd.getCDClass(i) == this.from) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the terminating class as an index in a class diagram,
     * which contains such class
     * @param cd the class diagram containing the class
     * @return the index of the class, or -1 if the class was not found
     */
    public int getToAsInt(ClassDiagram cd) {
        for (int i = 0; i < cd.classesLen(); i++) {
            if (cd.getCDClass(i) == this.to) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @return the cardinality at the originating class
     */
    public String getfCard() {
        return this.fCard;
    }

    /**
     *
     * @return the cardinality at the terminating class
     */
    public String gettCard() {
        return this.tCard;
    }

    /**
     *
     * @return the type of the node as an integer
     */
    public int getType() {
        return this.type.getNumVal();
    }

    public AnchorType getfAnchor() {
        return this.fAnchor;
    }

    public AnchorType gettAnchor() {
        return this.tAnchor;
    }
}
