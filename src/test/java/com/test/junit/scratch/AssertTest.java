package com.test.junit.scratch;

import com.test.junit.scratch.annotation.ExpectToFail;
import org.junit.*;
import org.junit.rules.ExpectedException;

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

    /**
     * exception이 InsufficientFundsException 가 발생하게 되면 테스트는 통과한다.
     * InsufficientFundsException 외의 오류가 발생하면 테스트는 통과하지 못한다.
     */
    @Test(expected = InsufficientFundsException.class)
    public void throwWhenWithdrawingTooMuch() {
        account.withdraw(100);
    }

    @Test
    public void throwsWhenWithdrawingTooMuchTry() {
        try {
            account.withdraw(100);
            fail();
        }
        catch (InsufficientFundsException expected) {
            assertThat(expected.getMessage()).isEqualTo("balance only 0");
        }
    }

    /**
     * 예외를 원하는 예외로 발생시키는 것.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionRule() {
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("balance only 0");
        account.withdraw(100);
    }

    /**
     * 예외적인 상황을 제외하고 예외가 발생하지 않는다.
    */
    @Test
    public void readsFromTestFile() throws IOException {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
    }

    /**
     * @Ignore를 통해 해당 테스트를 제외할 수 있다.
     * 설명 메시지는 선택사항이다.
     * JUnit 테스트 러너는 제외된 테스트를 알려준다.
     */
    @Test
    @Ignore("don't forget me! ")
    public void somethingWeCannotHandleRightNow() {
        // ...
    }
}
