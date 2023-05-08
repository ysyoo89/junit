package com.test.junit.scratch;

import com.test.junit.scratch.annotation.ExpectToFail;
import org.junit.*;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import java.io.*;
import java.util.*;

public class AssertTest {
    private Account account;

    @Before
    public void createAccount() {
        account = new Account("an account name");
    }

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }

        private static final long serialVersionUID = 1L;
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
        /**
         * 햄크레스트 매처는 기존에는 사용했지만 현재는 사용을 추천하지 않는 것 같다.
         * 그 이유로는 아무래도 java 버전이 올라감에 따라 함수형 메소드를 많이 사용하게 되면서
         * 햄크레스트 매처의 방식이 함수형 메소드 사용 방식과 차이가 있기 때문일 것이라고 생각한다.
         * 이 부분에 대해서는 좀 더 알아봐야할 것 같다.
        */

        assertThat(account.getBalance()).isEqualTo(100);
    }

    @Test
    @ExpectToFail
    @Ignore
    public void assertFailure() {
        assertThat(account.getName()).startsWith("xyz");
        assertThat(new String[] {"a", "b", "c"}).isEqualTo(new String[] {"a","b"});
        assertThat(Arrays.asList(new String[] {"a"})).isEqualTo(Arrays.asList(new String[] {"a", "b"}));
    }

    @Test
    public void assertSuccess() {
        assertThat(new String[] {"a", "b"}).isEqualTo(new String[] {"a", "b"});
        assertThat(Arrays.asList(new String[] {"a"})).isEqualTo(new String[] {"a"});
        assertThat(account.getName()).isNotEqualTo("plunderings");
        // null 체크는 굳이 많이 할 필요없도록 설계가 되어야하므로 너무 많은 것은 좋지 못하다.
        assertThat(account.getName()).isNotNull();
    }
}
