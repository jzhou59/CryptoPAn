package cn.junjiezhou;

public class sampleTest {
    public static void main(String[] args) {
        try {
            CryptoPAn cryptoPAn = new CryptoPAn("wordlessthanfoursavecodefromboom");
            String anonymizedIP = cryptoPAn.forward("199.32.34.122");
            System.out.print(anonymizedIP);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
