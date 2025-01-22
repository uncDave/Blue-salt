package com.blusalt.blusalt.dto;

;
import lombok.Getter;
import lombok.Setter;

public class SearchDto {


    @Getter
    @Setter
    public static class SearchDroneQuery {
        private String keyword;
        private int limit = 10;
        private int page = 0;
        private String sortBy = "createdDate";
        private String order = "DESC";
//        @DateTimeFormat(pattern = "yyMMddHHmmss")
        private String startDate;
//        @DateTimeFormat(pattern = "yyMMddHHmmss")
        private String endDate;
    }
    @Getter
    @Setter
    public static class DroneQuery{
        private String keyword;
        private int limit = 10;
        private int page = 0;
        private String sortBy = "created_date";
        private String order = "DESC";
        private String startDate;
        private String endDate;
    }
    @Getter
    @Setter
    public static class TransactionQueryResponse<T>{
        private T data;
        private int page;
        private int limit;
        private long total;
        private int totalPages;
    }
}
