package test.crawling.basic_code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class Crawling_example {

    // 예시코드
    public static void main(String[] args) {
        action();
    }

    public static String action() {

        String[] days = new String[]{"월", "화", "수", "목", "금", "토", "일"};

        String url = "https://everytime.kr/@zjmhATXF5czcDHm78zDZ";

        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);


        WebElement tableBody = driver.findElement(By.cssSelector(".tablebody"));
        String outer = tableBody.getAttribute("outerHTML");
        Document doc = Jsoup.parse(outer); // Jsoup을 사용하여 HTML 파싱


        Elements table_cols = doc.select(".cols");
        int idx = 0;
        for (Element table_col : table_cols) {

            System.out.println();
            System.out.println(days[idx] + "요일 수업정보");
            System.out.println();

            String col_html = table_col.html();
            Document document = Jsoup.parse(col_html);
            Elements elements = document.select("div.subject");

            for (Element e : elements) {

                String startTime = topToStartClock(getTop(e));
                String runningTime = heightToRunningTime(getHeight(e));
                List<String> contents = getContents(e);

                System.out.println("수업 시작시간 : " + startTime);
                System.out.println("수업 진행시간 : " + runningTime);
                System.out.println(contents);

                System.out.println();
            }

            // add logic

            idx++;
        }

        // WebDriver 종료
        driver.quit();
        return "end";
    }


    public static String getHeight(Element e) {
        String style = e.attr("style"); // 스타일 속성 값 얻기
        String[] styles = style.split(";"); // 세미콜론(;)을 기준으로 스타일 속성 분리

        for (String s : styles) {
            if (s.trim().startsWith("height")) { // height로 시작하는 스타일 속성 찾기
                String height = s.split(":")[1].trim(); // 콜론(:)을 기준으로 속성 이름과 값 분리
                return height;
            }
        }

        return null;
    }

    public static String getTop(Element e) {
        String style = e.attr("style"); // 스타일 속성 값 얻기
        String[] styles = style.split(";"); // 세미콜론(;)을 기준으로 스타일 속성 분리

        for (String s : styles) {
            if (s.trim().startsWith("top")) { // height로 시작하는 스타일 속성 찾기
                String top = s.split(":")[1].trim(); // 콜론(:)을 기준으로 속성 이름과 값 분리
                return top;
            }
        }

        return null;
    }

    // map이 더 유리할듯
    public static List<String> getContents(Element e) {

        List<String> results = new ArrayList<>();

        String contentHtml = e.html();
        Document docs = Jsoup.parse(contentHtml);
        Elements childElements = docs.getAllElements();
        for (Element child : childElements) {
            if (child.tagName().equals("h3")) {
                // System.out.println("과목명 : " + child.text());
                results.add(child.text());
            }
            if (child.tagName().equals("em")) {
                // System.out.println("교수명 : " + child.text());
                results.add(child.text());
            }
            if (child.tagName().equals("span")) {
                // System.out.println("강의실 : " + child.text());
                results.add(child.text());
            }
        }

        return results;
    }

    public static String heightToRunningTime(String stringValue) {
        // 문자열 길이 구하기
        int length = stringValue.length();

        // 뒤에서 2자리를 제외한 부분 추출
        String numericPart = stringValue.substring(0, length - 2);

        int intValue = Integer.parseInt(numericPart);

        //30분당 25PX, +1 고려
        intValue--;

        int result = intValue / 50;
        return String.valueOf(result); // 시간단위
    }

    public static String topToStartClock(String stringValue) {
        // 문자열 길이 구하기
        int length = stringValue.length();

        // 뒤에서 2자리를 제외한 부분 추출
        String numericPart = stringValue.substring(0, length - 2);

        int intValue = Integer.parseInt(numericPart);

        int result = intValue / 50; // 1시간 50px
        return String.valueOf(result); // 시간단위
    }
}
