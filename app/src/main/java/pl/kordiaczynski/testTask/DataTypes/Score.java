package pl.kordiaczynski.testTask.DataTypes;

/**
 * Created by kordiaczynski on 29.11.2016.
 */

public class Score {
    static String date;
    String team_A_name;
    String team_B_name;
    String fs_A;
    String fs_B;

    public Score(String team_A_name, String team_B_name, String fs_A, String fs_B) {
        this.team_A_name = team_A_name;
        this.team_B_name = team_B_name;
        this.fs_A = fs_A;
        this.fs_B = fs_B;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Score.date = date;
    }

    public String getTeam_A_name() {
        return team_A_name;
    }

    public void setTeam_A_name(String team_A_name) {
        this.team_A_name = team_A_name;
    }

    public String getTeam_B_name() {
        return team_B_name;
    }

    public void setTeam_B_name(String team_B_name) {
        this.team_B_name = team_B_name;
    }

    public String getFs_A() {
        return fs_A;
    }

    public void setFs_A(String fs_A) {
        this.fs_A = fs_A;
    }

    public String getFs_B() {
        return fs_B;
    }

    public void setFs_B(String fs_B) {
        this.fs_B = fs_B;
    }
}
