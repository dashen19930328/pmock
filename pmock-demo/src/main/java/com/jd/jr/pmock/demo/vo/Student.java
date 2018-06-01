package com.jd.jr.pmock.demo.vo;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-30
 * Time: 上午10:38
 */
public class Student extends PersonVo {
    private String school;//学校名字
    private Integer courseCount;//课程数量

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}
