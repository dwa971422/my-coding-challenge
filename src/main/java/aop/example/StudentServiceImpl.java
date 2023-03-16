package aop.example;

class StudentServiceImpl implements StudentService {
    @Override
    public void print() {
        System.out.println("this is print");
//        throw new RuntimeException();
    }


    @Override
    public int get() {
        System.out.println("this is get");
        return 0;
//        throw new RuntimeException();
    }

    @Override
    public void set() {
        System.out.println("this is set");
    }
}
