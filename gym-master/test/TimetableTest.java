import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group(
                "Акробатика для детей",
                Age.CHILD,
                60
        );

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        TrainingSession session = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(session);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondaySessions.size());
        assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        Group adultGroup = new Group(
                "Акробатика для взрослых",
                Age.ADULT,
                90
        );

        TrainingSession thursdayAdult = new TrainingSession(
                adultGroup,
                coach,
                DayOfWeek.THURSDAY,
                new TimeOfDay(20, 0)
        );

        Group childGroup = new Group(
                "Акробатика для детей",
                Age.CHILD,
                60
        );

        TrainingSession mondayChild = new TrainingSession(
                childGroup,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        TrainingSession thursdayChild = new TrainingSession(
                childGroup,
                coach,
                DayOfWeek.THURSDAY,
                new TimeOfDay(13, 0)
        );

        TrainingSession saturdayChild = new TrainingSession(
                childGroup,
                coach,
                DayOfWeek.SATURDAY,
                new TimeOfDay(10, 0)
        );

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(saturdayChild);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        assertEquals(1, mondaySessions.size());
        assertEquals(2, thursdaySessions.size());
        assertEquals(0, tuesdaySessions.size());

        assertEquals(
                thursdayChild,
                thursdaySessions.get(0)
        );

        assertEquals(
                thursdayAdult,
                thursdaySessions.get(1)
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group(
                "Акробатика для детей",
                Age.CHILD,
                60
        );

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        TrainingSession session = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(14, 0)
                );

        assertEquals(1, sessionsAt13.size());
        assertEquals(0, sessionsAt14.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        Group group1 = new Group(
                "Акробатика",
                Age.CHILD,
                60
        );

        Group group2 = new Group(
                "Гимнастика",
                Age.CHILD,
                60
        );

        TrainingSession session1 = new TrainingSession(
                group1,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        TrainingSession session2 = new TrainingSession(
                group2,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        assertEquals(2, sessions.size());
        assertEquals(session1, sessions.get(0));
        assertEquals(session2, sessions.get(1));
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(0, sessions.size());
    }

    @Test
    void testTrainingSessionsSortedByTime() {
        Timetable timetable = new Timetable();

        Group group = new Group(
                "Акробатика",
                Age.CHILD,
                60
        );

        Coach coach = new Coach(
                "Васильев",
                "Николай",
                "Сергеевич"
        );

        TrainingSession session18 = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(18, 0)
        );

        TrainingSession session10 = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)
        );

        TrainingSession session13 = new TrainingSession(
                group,
                coach,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(session18);
        timetable.addNewTrainingSession(session10);
        timetable.addNewTrainingSession(session13);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        assertEquals(session10, sessions.get(0));
        assertEquals(session13, sessions.get(1));
        assertEquals(session18, sessions.get(2));
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        Coach coach2 = new Coach(
                "Петров",
                "Пётр",
                "Петрович"
        );

        Group group = new Group(
                "Акробатика",
                Age.CHILD,
                60
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.WEDNESDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach2,
                        DayOfWeek.THURSDAY,
                        new TimeOfDay(10, 0)
                )
        );

        List<CounterOfTrainings> counters =
                timetable.getCountByCoaches();

        assertEquals(2, counters.size());

        assertEquals(coach1, counters.get(0).getCoach());
        assertEquals(3, counters.get(0).getCount());

        assertEquals(coach2, counters.get(1).getCoach());
        assertEquals(1, counters.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesWithNoTrainings() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> counters =
                timetable.getCountByCoaches();

        assertEquals(0, counters.size());
    }
}
