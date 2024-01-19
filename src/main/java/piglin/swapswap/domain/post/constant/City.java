package piglin.swapswap.domain.post.constant;

public enum City {
    ANDONG("안동시", Province.NORTH_GYEONGSANG),
    ANSAN("안산시", Province.GYEONGGI),
    ANSEONG("안성시", Province.GYEONGGI),
    ANYANG("안양시", Province.GYEONGGI),
    ASAN("아산시", Province.SOUTH_CHUNGCHEONG),
    BORYEONG("보령시", Province.SOUTH_CHUNGCHEONG),
    BUCHEON("부천시", Province.GYEONGGI),
    BUSAN("부산광역시", Province.NONE),
    CHANGWON("창원시", Province.SOUTH_GYEONGSANG),
    CHEONAN("천안시", Province.SOUTH_CHUNGCHEONG),
    CHEONGJU("청주시", Province.NORTH_CHUNGCHEONG),
    CHUNCHEON("춘천시", Province.GANGWON),
    CHUNGJU("충주시", Province.NORTH_CHUNGCHEONG),
    DAEGU("대구광역시", Province.NONE),
    DAEJEON("대전광역시", Province.NONE),
    DANGJIN("당진시", Province.SOUTH_CHUNGCHEONG),
    DONGDUCHEON("동두천시", Province.GYEONGGI),
    DONGHAE("동해시", Province.GANGWON),
    GANGNEUNG("강릉시", Province.GANGWON),
    GEOJE("거제시", Province.SOUTH_GYEONGSANG),
    GIMCHEON("김천시", Province.NORTH_GYEONGSANG),
    GIMHAE("김해시", Province.SOUTH_GYEONGSANG),
    GIMJE("김제시", Province.NORTH_JEOLLA),
    GIMPO("김포시", Province.GYEONGGI),
    GONGJU("공주시", Province.SOUTH_CHUNGCHEONG),
    GOYANG("고양시", Province.GYEONGGI),
    GUMI("구미시", Province.NORTH_GYEONGSANG),
    GUNPO("군포시", Province.GYEONGGI),
    GUNSAN("군산시", Province.NORTH_JEOLLA),
    GURI("구리시", Province.GYEONGGI),
    GWACHEON("과천시", Province.GYEONGGI),
    GWANGJU("광주광역시", Province.NONE),
    GWANGJU_GYEONGGI("광주시", Province.GYEONGGI),
    GWANGMYEONG("광명시", Province.GYEONGGI),
    GWANGYANG("광양시", Province.SOUTH_JEOLLA),
    GYEONGJU("경주시", Province.NORTH_GYEONGSANG),
    GYEONGSAN("경산시", Province.NORTH_GYEONGSANG),
    GYERYONG("계룡시", Province.SOUTH_CHUNGCHEONG),
    HANAM("하남시", Province.GYEONGGI),
    HWASEONG("화성시", Province.GYEONGGI),
    ICHEON("이천시", Province.GYEONGGI),
    IKSAN("익산시", Province.NORTH_JEOLLA),
    INCHEON("인천광역시", Province.NONE),
    JECHEON("제천시", Province.NORTH_CHUNGCHEONG),
    JEONGEUP("정읍시", Province.NORTH_JEOLLA),
    JEONJU("전주시", Province.NORTH_JEOLLA),
    JEJU("제주시", Province.JEJU),
    JINJU("진주시", Province.SOUTH_GYEONGSANG),
    NAJU("나주시", Province.SOUTH_JEOLLA),
    NAMYANGJU("남양주시", Province.GYEONGGI),
    NAMWON("남원시", Province.NORTH_JEOLLA),
    NONSAN("논산시", Province.SOUTH_CHUNGCHEONG),
    MIRYANG("밀양시", Province.SOUTH_GYEONGSANG),
    MOKPO("목포시", Province.SOUTH_JEOLLA),
    MUNGYEONG("문경시", Province.NORTH_GYEONGSANG),
    OSAN("오산시", Province.GYEONGGI),
    PAJU("파주시", Province.GYEONGGI),
    POCHEON("포천시", Province.GYEONGGI),
    POHANG("포항시", Province.NORTH_GYEONGSANG),
    PYEONGTAEK("평택시", Province.GYEONGGI),
    SACHEON("사천시", Province.SOUTH_GYEONGSANG),
    SANGJU("상주시", Province.NORTH_GYEONGSANG),
    SAMCHEOK("삼척시", Province.GANGWON),
    SEJONG("세종특별자치시", Province.NONE),
    SEOGWIPO("서귀포시", Province.JEJU),
    SEONGNAM("성남시", Province.GYEONGGI),
    SEOSAN("서산시", Province.SOUTH_CHUNGCHEONG),
    SEOUL("서울특별시", Province.NONE),
    SIHEUNG("시흥시", Province.GYEONGGI),
    SOKCHO("속초시", Province.GANGWON),
    SUNCHEON("순천시", Province.SOUTH_JEOLLA),
    SUWON("수원시", Province.GYEONGGI),
    TAEBACK("태백시", Province.GANGWON),
    TONGYEONG("통영시", Province.SOUTH_GYEONGSANG),
    UIJEONGBU("의정부시", Province.GYEONGGI),
    UIWANG("의왕시", Province.GYEONGGI),
    ULSAN("울산광역시", Province.NONE),
    WONJU("원주시", Province.GANGWON),
    YANGJU("양주시", Province.GYEONGGI),
    YANGSAN("양산시", Province.SOUTH_GYEONGSANG),
    YEOJU("여주시", Province.GYEONGGI),
    YEONGCHEON("영천시", Province.NORTH_GYEONGSANG),
    YEONGJU("영주시", Province.NORTH_GYEONGSANG),
    YEOSU("여수시", Province.SOUTH_JEOLLA),
    YONGIN("용인시", Province.GYEONGGI);

    private final String name;
    private final Province province;

    City(String name, Province province) {
        this.name = name;
        this.province = province;
    }

//    public static class CityName {
//
//        public static String ANDONG = "안동시";
//        public static String ANSAN = "안산시";
//        public static String ANSEONG = "안성시";
//        public static String ANYANG = "안양시";
//        public static String ASAN = "아산시";
//        public static String BORYEONG = "보령시";
//        public static String BUCHEON = "부천시";
//        public static String BUSAN = "부산광역시";
//        public static String CHANGWON = "창원시";
//        public static String CHEONAN = "천안시";
//        public static String CHEONGJU = "청주시";
//        public static String CHUNCHEON = "춘천시";
//        public static String CHUNGJU = "충주시";
//        public static String DAEGU = "대구광역시";
//        public static String DAEJEON = "대전광역시";
//        public static String DANGJIN = "당진시";
//        public static String DONGDUCHEON = "동두천시";
//        public static String DONGHAE = "동해시";
//        public static String GANGNEUNG = "강릉시";
//        public static String GEOJE = "거제시";
//        public static String GIMCHEON = "김천시";
//        public static String GIMHAE = "김해시";
//        public static String GIMJE = "김제시";
//        public static String GIMPO = "김포시";
//        public static String GONGJU = "공주시";
//        public static String GOYANG = "고양시";
//        public static String GUMI = "구미시";
//        public static String GUNPO = "군포시";
//        public static String GUNSAN = "군산시";
//        public static String GURI = "구리시";
//        public static String GWACHEON = "과천시";
//        public static String GWANGJU = "광주광역시";
//        public static String GWANGJU_GYEONGGI = "광주시";
//        public static String GWANGMYEONG = "광명시";
//        public static String GWANGYANG = "광양시";
//        public static String GYEONGJU = "경주시";
//        public static String GYEONGSAN = "경산시";
//        public static String GYERYONG = "계룡시";
//        public static String HANAM = "하남시";
//        public static String HWASEONG = "화성시";
//        public static String ICHEON = "이천시";
//        public static String IKSAN = "익산시";
//        public static String INCHEON = "인천광역시";
//        public static String JECHEON = "제천시";
//        public static String JEONGEUP = "정읍시";
//        public static String JEONJU = "전주시";
//        public static String JEJU = "제주시";
//        public static String JINJU = "진주시";
//        public static String NAJU = "나주시";
//        public static String NAMYANGJU = "남양주시";
//        public static String NAMWON = "남원시";
//        public static String NONSAN = "논산시";
//        public static String MIRYANG = "밀양시";
//        public static String MOKPO = "목포시";
//        public static String MUNGYEONG = "문경시";
//        public static String OSAN = "오산시";
//        public static String PAJU = "파주시";
//        public static String POCHEON = "포천시";
//        public static String POHANG = "포항시";
//        public static String PYEONGTAEK = "평택시";
//        public static String SACHEON = "사천시";
//        public static String SANGJU = "상주시";
//        public static String SAMCHEOK = "삼척시";
//        public static String SEJONG = "세종특별자치시";
//        public static String SEOGWIPO = "서귀포시";
//        public static String SEONGNAM = "성남시";
//        public static String SEOSAN = "서산시";
//        public static String SEOUL = "서울특별시";
//        public static String SIHEUNG = "시흥시";
//        public static String SOKCHO = "속초시";
//        public static String SUNCHEON = "순천시";
//        public static String SUWON = "수원시";
//        public static String TAEBACK = "태백시";
//        public static String TONGYEONG = "통영시";
//        public static String UIJEONGBU = "의정부시";
//        public static String UIWANG = "의왕시";
//        public static String ULSAN = "울산광역시";
//        public static String WONJU = "원주시";
//        public static String YANGJU = "양주시";
//        public static String YANGSAN = "양산시";
//        public static String YEOJU = "여주시";
//        public static String YEONGCHEON = "영천시";
//        public static String YEONGJU = "영주시";
//        public static String YEOSU = "여수시";
//        public static String YONGIN = "용인시";
//    }
}
