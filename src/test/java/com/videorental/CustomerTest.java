package com.videorental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    public static final String NAME = "NAME_NOT_IMPORTANT";
    public static final String TITLE = "TITLE_NOT_IMPORTANT";

    Customer customer = new Customer(NAME);

    // 아래 테스트에서 사용 될 rental 생성 코드
    private static Rental createRentalFor(int priceCode, int daysRented) {
        Movie movie = getMovie(priceCode);
        return new Rental(movie, daysRented);
    }

    private static Movie getMovie(int priceCode) {
        switch (priceCode){
            case Movie.REGULAR:
                return new RegularMovie(TITLE);
            case Movie.NEW_RELEASE:
                return new NewReleaseMovie(TITLE);
            case Movie.CHILDRENS:
                return new ChildrenMovie(TITLE);
            default :
                return null;
        }
    }

    @Test
    @DisplayName("[01] Customer 생성 테스트")
    public void returnNewCustomer() {
        Assertions.assertNotNull(customer);
    }

    @Test
    @DisplayName("[02] Movie를 Rental 하지 않은 경우")
    public void statementForNoRental() {
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "Amount owed is 0.0\n" +
                "You earned 0 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[03] Amount 계산 로직 테스트 : Regular movie (1/2)")
    public void statementForRegularMovieRentalForLessThan3Days() {
        // given
        customer.addRental(createRentalFor(Movie.REGULAR, 2));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t2.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 2.0\n"
                + "You earned 1 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[04] Amount 계산 로직 테스트 : Regular movie (2/2)")
    public void statementForRegularMovieRentalForMoreThan2Days() {
        // given
        customer.addRental(createRentalFor(Movie.REGULAR, 3));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.5(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.5\n"
                + "You earned 1 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[05] Amount 계산 로직 테스트 : New release movie")
    public void statementForNewReleaseMovie() {
        // given
        customer.addRental(createRentalFor(Movie.NEW_RELEASE, 1));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 3.0\n" +
                "You earned 1 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[06] Amount 계산 로직 테스트 : Childrens movie (1/2)")
    public void statementForChildrensMovieRentalMoreThan3Days() {
        // given
        customer.addRental(createRentalFor(Movie.CHILDRENS, 4));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.0\n"
                + "You earned 1 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[07] Amount 계산 로직 테스트 : Childrens movie (2/2)")
    public void statementForChildrensMovieRentalLessThan4Days() {
        // given
        customer.addRental(createRentalFor(Movie.CHILDRENS, 3));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t1.5(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 1.5\n" +
                "You earned 1 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[08] Frequent renter points 계산 로직")
    public void statementForNewReleaseMovieRentalMoreThan1Day() {
        // given
        customer.addRental(createRentalFor(Movie.NEW_RELEASE, 2));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t6.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 6.0\n" +
                "You earned 2 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[09] Movie 여러 개를 대여한 경우")
    public void statementForFewMovieRental() {
        // given
        customer.addRental(createRentalFor(Movie.REGULAR, 1));
        customer.addRental(createRentalFor(Movie.NEW_RELEASE, 4));
        customer.addRental(createRentalFor(Movie.CHILDRENS, 4));

        // when then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t2.0(TITLE_NOT_IMPORTANT)\n" +
                "\t12.0(TITLE_NOT_IMPORTANT)\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 17.0\n" +
                "You earned 4 frequent renter pointers", customer.statement());
    }

    @Test
    @DisplayName("[10] 가격이 변동된 경우")
    public void changeMoviePrice() {
        // given
        Movie movie = new NewReleaseMovie(TITLE);

        // when
        movie.setPriceCode(20);

        // then
        Assertions.assertEquals(20, movie.getPriceCode());
    }
}
