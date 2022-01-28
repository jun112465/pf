package jun.studyHelper;

public class JavaTest {

    public static void main(String[] args) {
        String friendList = "-20220108-20220108-20220108-20220108-55443322-980605-981124-20220108-20220108-2022010802";
//        String find = "55443322";
//        String find = String.valueOf("55443322");
        String find = Integer.toString(20220108);
        int idx = friendList.indexOf(find);
        System.out.println(idx);
        String s1 = friendList.substring(0, idx);
        String s2 = friendList.substring(idx+find.length()+1);
        String s3 = s1 + s2;
        System.out.println(s3);

    }
}
