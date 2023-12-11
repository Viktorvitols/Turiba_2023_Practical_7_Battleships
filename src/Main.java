public class Main {
    public static void main(String[] args) {
        Battlefield battlefield = new Battlefield();
        battlefield.drawBattlefield();
        System.out.println("To deploy your ships you need to choose one's direction ('V' for vertical or 'H' for horizontal) and an index for the left upper end of the ship (e.g. VG6)");
        battlefield.setUpFleet();
    }
}