package Java_Practice.week_2;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


class Student{
    String name;
    String grade;
    String subject;
    double scores;

    Student(String name, String grade, String subject, double scores){
        this.name = name;
        this.grade = grade;
        this.subject = subject;
        this.scores = scores;
    }

    public double getscore(){
        return scores;
    }

    public String getgrade(){
        return grade;
    }
    public String getname(){
        return name;
    }

    public String getsubject(){
        return subject;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + scores +
                '}';
    }
};
public class student_scoresAnalyzer {
    

 
    public static void main(String[] args) {
        List<Student> students = List.of(
            new Student("Alice",   "A", "Math",    92),
            new Student("Bob",     "B", "Math",    75),
            new Student("Charlie", "A", "Science", 88),
            new Student("Diana",   "C", "Math",    60),
            new Student("Ethan",   "B", "Science", 81),
            new Student("Farah",   "A", "English", 95),
            new Student("George",  "C", "English", 55),
            new Student("Hana",    "B", "Math",    70),
            new Student("Ivan",    "A", "Science", 99),
            new Student("Julia",   "C", "Science", 65),
            new Student("Kevin",   "B", "English", 78),
            new Student("Liam",    "A", "Math",    84)
        );

        //Sort all students by score, descending
        List<Student> s = students
                .stream()
                .sorted(Comparator.comparingDouble(Student::getscore).reversed())
                .toList();
       s.forEach(System.out::println);
       
       //Group by grade (groupingBy)
        Map<String, List<Student> > s1 = students
            .stream()
            .collect(Collectors.groupingBy(Student::getgrade));
        s1.entrySet().forEach(System.out::println);

        //Group by subject + average (groupingBy + averagingInt)
        Map<String,Double> s2 = students
        .stream()
        .collect(Collectors.groupingBy(
            Student::getsubject, 
            Collectors.averagingDouble(Student::getscore)
        ));

        s2.forEach((grade, average) ->
        System.out.println(grade + ": " + average));

        //Top 3 overall (sorted + limit)

        List<Student> s3 = students
        .stream()
        .sorted(Comparator.comparingDouble(Student::getscore).reversed())
        .limit(3)
        .toList();

        s3.forEach(System.out::println);
        

        //Top 2 per subject (nested grouping + sort + limit — the classic "top N per group" pattern)
        Map<String, List<Student>> top2PerSubject = students.stream()
        .collect(Collectors.groupingBy(Student::getsubject,
                Collectors.collectingAndThen(
                        Collectors.toList(),
                        group -> group.stream()
                                .sorted(Comparator.comparingDouble(Student::getscore).reversed())
                                .limit(2)
                                .toList()
                )
        ));

        top2PerSubject.forEach((subject, topStudents) -> {
    System.out.println("\nSubject: " + subject);

    topStudents.forEach(student ->
            System.out.println(
                    student.getname() + " - " + student.getscore()
            )
    );
    });

    //Highest scorer per grade (groupingBy + maxBy)

    Map<String, Optional<Student>> top_score = students
        .stream()
        .collect(Collectors.groupingBy(Student::getgrade,
            Collectors.maxBy(Comparator.comparingDouble(Student::getscore))
        ));
        top_score.forEach((grade, studentOpt) -> {
            studentOpt.ifPresent(student -> 
                System.out.println("Grade: " + grade + " | Top Student: " + student.getname() + " (" + student.getscore() + ")")
            );
        });


    //Count per subject (groupingBy + counting)
    Map<String , Long> c = students
    .stream()
    .collect(Collectors.groupingBy(Student::getsubject,
        Collectors.counting()
    ));
    System.out.println(c);



    //Pass/Fail split (partitioningBy)

    Map<Boolean, List<Student>> split = students
    .stream()
    .collect(Collectors.partitioningBy(o->o.getscore() > 80));

    System.out.println("student passed exam: " + split.get(true));
    System.out.println("student failed exam: " + split.get(false));



    
    //Summary stats — min/max/avg/sum in one call (summaryStatistics)

    DoubleSummaryStatistics stats = students
    .stream()
    .mapToDouble(Student::getscore)
    .summaryStatistics();

    System.out.println("Count: "   + stats.getCount());   
    System.out.println("Sum: "     + stats.getSum());     
    System.out.println("Average: " + stats.getAverage()); 
    System.out.println("Min: "     + stats.getMin());     
    System.out.println("Max: "     + stats.getMax());  
    
    
    //Joined, sorted names (map + sorted + joining)

    String Joined = students
    .stream()
    .map(Student::getname)
    .sorted()
    .collect(Collectors.joining(", "));

    System.out.println(Joined);




    }

}
