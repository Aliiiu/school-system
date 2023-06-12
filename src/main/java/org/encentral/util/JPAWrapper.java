package org.encentral.util;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.*;

public class JPAWrapper {
    private static EntityManagerFactory emf;

//    public JPAWrapper(String pu){
//        this.emf = Persistence.createEntityManagerFactory(pu);
//        this.entityManager = this.emf.createEntityManager();
//        this.queryFactory = new JPAQueryFactory(entityManager);
//    }

    public static EntityManager getEntityManager(String pu){
        emf = Persistence.createEntityManagerFactory(pu);
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }
}
