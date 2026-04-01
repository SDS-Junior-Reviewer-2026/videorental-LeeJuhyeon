package com.videorental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    @DisplayName("[01] Customer 생성 테스트")
    public void returnNewCustomer() {
        // given, when
        Customer customer = new Customer("NAME_NOT_IMPORTANT");

        // then
        Assertions.assertNotNull(customer);
    }

    @Test
    @DisplayName("[02] Movie를 Rental 하지 않은 경우")
    public void statementForNoRental() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "Amount owed is 0.0\n" +
                "You earned 0 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[03] Amount 계산 로직 테스트 : Regular movie (1/2)")
    public void statementForRegularMovieRentalForLessThan3Days() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.REGULAR);
        int daysRented = 2;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t2.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 2.0\n"
                + "You earned 1 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[04] Amount 계산 로직 테스트 : Regular movie (2/2)")
    public void statementForRegularMovieRentalForMoreThan2Days() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.REGULAR);
        int daysRented = 3;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.5(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.5\n"
                + "You earned 1 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[05] Amount 계산 로직 테스트 : New release movie")
    public void statementForNewReleaseMovie() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.NEW_RELEASE);
        int daysRented = 1;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 3.0\n" +
                "You earned 1 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[06] Amount 계산 로직 테스트 : Childrens movie (1/2)")
    public void statementForChildrensMovieRentalMoreThan3Days() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.CHILDRENS);
        int daysRented = 4;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n"
                + "\t3.0(TITLE_NOT_IMPORTANT)\n"
                + "Amount owed is 3.0\n"
                + "You earned 1 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[07] Amount 계산 로직 테스트 : Childrens movie (2/2)")
    public void statementForChildrensMovieRentalLessThan4Days() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.CHILDRENS);
        int daysRented = 3;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t1.5(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 1.5\n" +
                "You earned 1 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[08] Frequent renter points 계산 로직")
    public void statementForNewReleaseMovieRentalMoreThan1Day() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie movie = new Movie("TITLE_NOT_IMPORTANT", Movie.NEW_RELEASE);
        int daysRented = 2;
        Rental rental = new Rental(movie, daysRented);
        customer.addRental(rental);

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t6.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 6.0\n" +
                "You earned 2 frequent renter pointers", statement);
    }

    @Test
    @DisplayName("[09] Movie 여러 개를 대여한 경우")
    public void statementForFewMovieRental() {
        // given
        Customer customer = new Customer("NAME_NOT_IMPORTANT");
        Movie regularMovie = new Movie("TITLE_NOT_IMPORTANT", Movie.REGULAR);
        Movie newReleaseMovie = new Movie("TITLE_NOT_IMPORTANT", Movie.NEW_RELEASE);
        Movie childrensMovie = new Movie("TITLE_NOT_IMPORTANT", Movie.CHILDRENS);
        customer.addRental(new Rental(regularMovie, 1));
        customer.addRental(new Rental(newReleaseMovie, 4));
        customer.addRental(new Rental(childrensMovie, 4));

        // when
        String statement = customer.statement();

        // then
        Assertions.assertEquals("Rental Record for NAME_NOT_IMPORTANT\n" +
                "\t2.0(TITLE_NOT_IMPORTANT)\n" +
                "\t12.0(TITLE_NOT_IMPORTANT)\n" +
                "\t3.0(TITLE_NOT_IMPORTANT)\n" +
                "Amount owed is 17.0\n" +
                "You earned 4 frequent renter pointers", statement);
    }

}
