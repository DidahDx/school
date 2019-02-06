package sample.model.examination;

/**
 * THIS CLASS IS USED TO GENERATE THE GRADE AND POINTS OF A SUBJECT
 * */


public class GradingSubjects {

    //this method generates grades the subject
    public String GradeGenerator(int subject){
        String grade = null;

        if((80 <= subject) && (subject<= 100)){
            grade="A";
        }else if ((79 <= subject) && (subject<= 75)){
            grade="A-";
        }else if ((70 <= subject) && (subject<= 74)){
            grade="B+";
        }else if ((65 <= subject) && (subject<= 69)){
            grade="B";
        }else if ((60 <= subject) && (subject<= 64)){
            grade="B-";
        }else if ((55 <= subject) && (subject<= 59)){
            grade="C+";
        }else if ((45 <= subject) && (subject<= 54)){
            grade="C";
        }else if ((40 <= subject) && (subject<= 44)){
            grade="C-";
        }else if ((35 <= subject) && (subject<= 39)){
            grade="D+";
        }else if ((30 <= subject) && (subject<= 34)){
            grade="D";
        }else if ((25 <= subject) && (subject<= 29)){
            grade="D-";
        }else{
            grade="E";
        }

        return  grade;
    }

    //this method generates the point the subject
    public int pointsGenerator(int subject){
        int points = 0;

        if((80 <= subject) && (subject<= 100)){
            points=12;
        }else if ((79 <= subject) && (subject<= 75)){
            points=11;
        }else if ((70 <= subject) && (subject<= 74)){
            points=10;
        }else if ((65 <= subject) && (subject<= 69)){
            points=9;
        }else if ((60 <= subject) && (subject<= 64)){
            points=8;
        }else if ((55 <= subject) && (subject<= 59)){
            points=7;
        }else if ((45 <= subject) && (subject<= 54)){
            points=6;
        }else if ((40 <= subject) && (subject<= 44)){
            points=5;
        }else if ((35 <= subject) && (subject<= 39)){
            points=4;
        }else if ((30 <= subject) && (subject<= 34)){
            points=3;
        }else if ((25 <= subject) && (subject<= 29)){
            points=2;
        }else{
            points=1;
        }

        return  points;
    }
}
