public class TypistTest {

    private static int total = 0;
    private static int passed = 0;

    private static void check(String testName, boolean condition) {
        total++;
        if (condition) {
            passed++;
            System.out.println("[PASS] " + testName);
        } else {
            System.out.println("[FAIL] " + testName);
        }
    }

    public static void main(String[] args) {

        Typist t = new Typist('②', "EDGE_CASE_TESTER", 0.5);

        // =========================
        // 1. Forward movement
        // =========================
        t.typeCharacter();
        t.typeCharacter();
        check("typeCharacter increments correctly", t.getProgress() == 2);
        System.out.println("   Output: " + t.getProgress() + " | Expected: 2\n");

        // =========================
        // 2. slideBack normal case
        // =========================
        t.slideBack(1);
        check("slideBack reduces progress correctly", t.getProgress() == 1);
        System.out.println("   Input: slideBack(1)");
        System.out.println("   Output: " + t.getProgress() + " | Expected: 1\n");

        // =========================
        // 3. slideBack edge cases
        // =========================
        t.slideBack(5);
        check("slideBack clamps to zero when overshooting", t.getProgress() == 0);
        System.out.println("   Input: slideBack(5)");
        System.out.println("   Output: " + t.getProgress() + " | Expected: 0\n");

        t.slideBack(0);
        check("slideBack with 0 does nothing", t.getProgress() == 0);
        System.out.println("   Input: slideBack(0)");
        System.out.println("   Output: " + t.getProgress() + " | Expected: 0\n");

        // =========================
        // 4. Burnout behaviour
        // =========================
        t.burnOut(2);
        check("burnOut sets burnt state", t.isBurntOut());
        System.out.println("   Output: " + t.isBurntOut() + " | Expected: true");

        check("burnOut sets correct turns", t.getBurnoutTurnsRemaining() == 2);
        System.out.println("   Output: " + t.getBurnoutTurnsRemaining() + " | Expected: 2\n");

        t.recoverFromBurnout();
        check("burnout decreases after 1 turn", t.getBurnoutTurnsRemaining() == 1);
        System.out.println("   Output: " + t.getBurnoutTurnsRemaining() + " | Expected: 1\n");

        t.recoverFromBurnout();
        check("burnout clears at zero", !t.isBurntOut() && t.getBurnoutTurnsRemaining() == 0);
        System.out.println("   Output: " + t.isBurntOut() + ", " + t.getBurnoutTurnsRemaining() + " | Expected: false, 0\n");

        // =========================
        // 5. recover when NOT burnt out
        // =========================
        t.recoverFromBurnout();
        check("recoverFromBurnout does nothing when not burnt out", t.getBurnoutTurnsRemaining() == 0);
        System.out.println("   Output: " + t.getBurnoutTurnsRemaining() + " | Expected: 0\n");

        // =========================
        // 6. resetToStart
        // =========================
        t.typeCharacter();
        t.typeCharacter();
        t.burnOut(3);
        t.resetToStart();

        check("reset clears progress", t.getProgress() == 0);
        System.out.println("   Output: " + t.getProgress() + " | Expected: 0");

        check("reset clears burnout state", !t.isBurntOut());
        System.out.println("   Output: " + t.isBurntOut() + " | Expected: false");

        check("reset clears burnout turns", t.getBurnoutTurnsRemaining() == 0);
        System.out.println("   Output: " + t.getBurnoutTurnsRemaining() + " | Expected: 0\n");

        // =========================
        // 7. Accuracy clamping
        // =========================
        t.setAccuracy(-1.0);
        check("accuracy clamps below 0", t.getAccuracy() == 0.0);
        System.out.println("   Output: " + t.getAccuracy() + " | Expected: 0.0");

        t.setAccuracy(2.0);
        check("accuracy clamps above 1", t.getAccuracy() == 1.0);
        System.out.println("   Output: " + t.getAccuracy() + " | Expected: 1.0");

        t.setAccuracy(0.6);
        check("accuracy sets valid value", t.getAccuracy() == 0.6);
        System.out.println("   Output: " + t.getAccuracy() + " | Expected: 0.6\n");

        // =========================
        // 8. Symbol update
        // =========================
        t.setSymbol('③');
        check("setSymbol updates symbol", t.getSymbol() == '③');
        System.out.println("   Output: " + t.getSymbol() + " | Expected: ③\n");

        // =========================
        // 9. Multiple actions sequence
        // =========================
        t.resetToStart();
        t.typeCharacter();
        t.typeCharacter();
        t.slideBack(1);
        t.typeCharacter();

        check("mixed actions produce correct progress", t.getProgress() == 2);
        System.out.println("   Output: " + t.getProgress() + " | Expected: 2\n");

        // =========================
        // Summary
        // =========================
        System.out.println("FINAL RESULT: " + passed + "/" + total + " tests passed.");
    }
}