package business.tools;

public enum TypeSite {
    HISTORIC,
    HOBBIES;

    public static TypeSite fromString(String text) {
        if (text.equalsIgnoreCase("historic")) {
            return HISTORIC;
        } else if (text.equalsIgnoreCase("activity")) {
            return HOBBIES;
        } else {
            throw new IllegalArgumentException("Type de site inconnu: " + text);
        }
    }
}
