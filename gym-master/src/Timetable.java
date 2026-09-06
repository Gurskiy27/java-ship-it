import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();

        Comparator<TimeOfDay> timeComparator = (time1, time2) -> {
            if (time1.getHours() != time2.getHours()) {
                return Integer.compare(time1.getHours(), time2.getHours());
            }

            return Integer.compare(time1.getMinutes(), time2.getMinutes());
        };

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            timetable.put(dayOfWeek, new TreeMap<>(timeComparator));
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.get(dayOfWeek);

        List<TrainingSession> sessions =
                daySchedule.computeIfAbsent(timeOfDay, key -> new ArrayList<>());

        sessions.add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        List<TrainingSession> result = new ArrayList<>();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.get(dayOfWeek);

        for (TimeOfDay timeOfDay : daySchedule.navigableKeySet()) {
            result.addAll(daySchedule.get(timeOfDay));
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay) {

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                timetable.get(dayOfWeek);

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);

        if (sessions == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(sessions);
    }
    public List<CounterOfTrainings> getCountByCoaches() {
        Map<String, CounterOfTrainings> counters = new HashMap<>();

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            TreeMap<TimeOfDay, List<TrainingSession>> daySchedule =
                    timetable.get(dayOfWeek);

            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {

                    Coach coach = session.getCoach();

                    String coachKey = coach.getSurname()
                            + " "
                            + coach.getName()
                            + " "
                            + coach.getMiddleName();

                    CounterOfTrainings counter = counters.get(coachKey);

                    if (counter == null) {
                        counters.put(
                                coachKey,
                                new CounterOfTrainings(coach, 1)
                        );
                    } else {
                        counters.put(
                                coachKey,
                                new CounterOfTrainings(
                                        coach,
                                        counter.getCount() + 1
                                )
                        );
                    }
                }
            }
        }

        List<CounterOfTrainings> result =
                new ArrayList<>(counters.values());

        result.sort(
                Comparator.comparingInt(CounterOfTrainings::getCount)
                        .reversed()
        );

        return result;
    }
}

