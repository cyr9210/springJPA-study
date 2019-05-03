package bong.springjpa02;

import bong.springjpa02.account.Account;
import bong.springjpa02.study.Study;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("choi");
        account.setPassword("pass");

        Study study = new Study();
        study.setName("spring data JPA");

        account.add(study);
//        entityManager.persist(account);

        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);

        Account bong = session.load(Account.class, account.getId());
        bong.setUsername("bong");
        System.out.println("============================");
        System.out.println(bong.getUsername());
     }
}
