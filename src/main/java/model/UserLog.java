package model;

public final class UserLog extends User {
    private static UserLog INSTANCE;

    private UserLog(User utilisateur) {
        super(utilisateur.getId(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getMail(), utilisateur.getMotDePasse(), utilisateur.getRefRole(), utilisateur.getDateCreation());
    }

    public static boolean initInstance(User utilisateur) {
        if (INSTANCE == null) {
            INSTANCE = new UserLog(utilisateur);

            System.out.println("User instance init");
            return true;
        }
        return false;
    }

    public static UserLog getInstance() {
        return INSTANCE;
    }

    public static boolean clearInstance() {
        if (INSTANCE != null) {
            INSTANCE = null;

            System.out.println("User instance clear");
            return true;
        }
        return false;
    }
}
