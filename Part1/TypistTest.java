public class TypistTest
{
    private static int total = 0;
    private static int passed = 0;

    private static void check(String testName, boolean condition)
    {
        total++;
        if (condition)
        {
            passed++;
            System.out.println("[PASS] " + testName);
        }
        else
        {
            System.out.println("[FAIL] " + testName);
        }
    }

    public static void main(String[] args)
    {
        Typist t = new Typist('②', "EDGE_CASE_TESTER", 0.5);

        // =========================
        // 1. Forward movement
        // =========================
        t.typeCharacter();
        t.typeCharacter();
        check("typeCharacter increments correctly", t.getProgress() == 2);

        // =========================
        // 2. slideBack normal case
        // =========================
        t.slideBack(1);
        check("slideBack reduces progress correctly", t.getProgress() == 1);

        // =========================
        // 3. slideBack edge cases
        // =========================
        t.slideBack(5); // should clamp to 0
        check("slideBack clamps to zero when overshooting", t.getProgress() == 0);

        t.slideBack(0); // should do nothing
        check("slideBack with 0 does nothing", t.getProgress() == 0);

        // =========================
        // 4. Burnout behaviour
        // =========================
        t.burnOut(2);
        check("burnOut sets burnt state", t.isBurntOut());
        check("burnOut sets correct turns", t.getBurnoutTurnsRemaining() == 2);

        t.recoverFromBurnout();
        check("burnout decreases after 1 turn", t.getBurnoutTurnsRemaining() == 1);

        t.recoverFromBurnout();
        check("burnout clears at zero", !t.isBurntOut() && t.getBurnoutTurnsRemaining() == 0);

        // =========================
        // 5. recover when NOT burnt out (edge case)
        // =========================
        t.recoverFromBurnout(); // should not go negative
        check("recoverFromBurnout does nothing when not burnt out", t.getBurnoutTurnsRemaining() == 0);

        // =========================
        // 6. resetToStart
        // =========================
        t.typeCharacter();
        t.typeCharacter();
        t.burnOut(3);
        t.resetToStart();

        check("reset clears progress", t.getProgress() == 0);
        check("reset clears burnout state", !t.isBurntOut());
        check("reset clears burnout turns", t.getBurnoutTurnsRemaining() == 0);

        // =========================
        // 7. Accuracy clamping
        // =========================
        t.setAccuracy(-1.0);
        check("accuracy clamps below 0", t.getAccuracy() == 0.0);

        t.setAccuracy(2.0);
        check("accuracy clamps above 1", t.getAccuracy() == 1.0);

        t.setAccuracy(0.6);
        check("accuracy sets valid value", t.getAccuracy() == 0.6);

        // =========================
        // 8. Symbol update
        // =========================
        t.setSymbol('③');
        check("setSymbol updates symbol", t.getSymbol() == '③');

        // =========================
        // 9. Multiple actions sequence
        // =========================
        t.resetToStart();
        t.typeCharacter();
        t.typeCharacter();
        t.slideBack(1);
        t.typeCharacter();

        check("mixed actions produce correct progress", t.getProgress() == 2);

        // =========================
        // Summary
        // =========================
        System.out.println("\nFINAL RESULT: " + passed + "/" + total + " tests passed.");
    }
}