import java.util.Random;

public class Lesson_4_1 {

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;

    public static int[] heroesHealth = {270, 260, 250, 240, 230};
    public static int[] heroesDamage = {20, 15, 10, 5, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Lucky", "Medic"};

    public static int healAmount = 30; // Лечение
    public static int dodgeChance = 20; // Шанс уклонения
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        medicHeal(); // Вызов метода лечения
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 2); // Без медика и Lucky
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        Random random = new Random();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesAttackType[i].equals("Lucky")) { // Проверяем, является ли это Lucky
                    int chance = random.nextInt(100); // Шанс от 0 до 99
                    if (chance < dodgeChance) { // Если Lucky уклонился
                        System.out.println("Lucky dodged the attack!");
                        continue; // Пропускаем получение урона
                    }
                }
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length - 1; i++) { // Медик не атакует
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2-10
                    damage *= coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void medicHeal() {
        if (heroesHealth[3] > 0) { // Проверяем, жив ли медик
            for (int i = 0; i < heroesHealth.length - 1; i++) { // Медик не лечит себя
                if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    heroesHealth[i] = heroesHealth[i] + healAmount;
                    System.out.println("Medic healed " + heroesAttackType[i] + " for " + healAmount + " health.");
                    break; // Лечит только одного героя за раунд
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("Round " + roundNumber + " -----------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
