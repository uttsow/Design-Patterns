package courseplanner.state;




public interface CoursePlannerStateI{
    void initalize();
    boolean takeCourse(String courseIn);
    void incrementCoursesTaken();
    int getCourseTakenCounter();
    void setGroupReq(boolean standingIn);
    boolean getGroupReq();
    void tookPre(boolean tookIn);
    boolean getPreReq();
}
