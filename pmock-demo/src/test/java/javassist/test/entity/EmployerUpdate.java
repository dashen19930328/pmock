package javassist.test.entity;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 上午11:05
 */
public interface EmployerUpdate    {
    public void queryVoid(Employer employer);

    public Employer update(Employer employer);

    public Employer query(Employer employer);
}
