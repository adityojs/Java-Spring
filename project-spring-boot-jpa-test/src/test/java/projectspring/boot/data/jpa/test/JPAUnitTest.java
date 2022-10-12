package projectspring.boot.data.jpa.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
    public class JPAUnitTest {

        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        TaskRepository repository;

        @Test
        public void should_find_no_tutorials_if_repository_is_empty() {
            Iterable tutorials = repository.findAll();

            assertThat(tutorials).isEmpty();
        }

        @Test
        public void should_store_a_tutorial() {
            Task task = repository.save(new Task("Tut title", "Tut desc", true));

            assertThat(Task).hasFieldOrPropertyWithValue("title", "Tut title");
            assertThat(Task).hasFieldOrPropertyWithValue("description", "Tut desc");
            assertThat(Task).hasFieldOrPropertyWithValue("status", true);
        }

        @Test
        public void should_find_all_tutorials() {
            Task tut1 = new Task("Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task tut3 = new Task("Tut#3", "Desc#3", true);
            entityManager.persist(tut3);

            Iterable tutorials = repository.findAll();

            assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
        }

        @Test
        public void should_find_tutorial_by_id() {
            Task tut1 = new Task("Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task foundTutorial = repository.findById(tut2.getId()).get();

            assertThat(foundTutorial).isEqualTo(tut2);
        }

        @Test
        public void should_find_published_tutorials() {
            Task tut1 = new Task("Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task tut3 = new Task("Tut#3", "Desc#3", true);
            entityManager.persist(tut3);

            Iterable tutorials = repository.findByStatus(true);

            assertThat(tutorials).hasSize(2).contains(tut1, tut3);
        }

        @Test
        public void should_find_tutorials_by_title_containing_string() {
            Task tut1 = new Task("Spring Boot Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Java Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task tut3 = new Task("Spring Data JPA Tut#3", "Desc#3", true);
            entityManager.persist(tut3);

            Iterable tutorials = repository.findByTitleContaining("ring");

            assertThat(tutorials).hasSize(2).contains(tut1, tut3);
        }

        @Test
        public void should_update_tutorial_by_id() {
            Task tut1 = new Task("Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task updatedTut = new Task("updated Tut#2", "updated Desc#2", true);

            Tutorial tut = repository.findById(tut2.getId()).get();
            tut.setTitle(updatedTut.getTitle());
            tut.setDescription(updatedTut.getDescription());
            tut.setStatus(updatedTut.isStatus());
            repository.save(tut);

            Tutorial checkTut = repository.findById(tut2.getId()).get();

            assertThat(checkTut.getId()).isEqualTo(tut2.getId());
            assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
            assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
            assertThat(checkTut.isStatus()).isEqualTo(updatedTut.isStatus());
        }

        @Test
        public void should_delete_tutorial_by_id() {
            Task tut1 = new Task("Tut#1", "Desc#1", true);
            entityManager.persist(tut1);

            Task tut2 = new Task("Tut#2", "Desc#2", false);
            entityManager.persist(tut2);

            Task tut3 = new Task("Tut#3", "Desc#3", true);
            entityManager.persist(tut3);

            repository.deleteById(tut2.getId());

            Iterable tutorials = repository.findAll();

            assertThat(tutorials).hasSize(2).contains(tut1, tut3);
        }

        @Test
        public void should_delete_all_tutorials() {
            entityManager.persist(new Task("Tut#1", "Desc#1", true));
            entityManager.persist(new Task("Tut#2", "Desc#2", false));

            repository.deleteAll();

            assertThat(repository.findAll()).isEmpty();
        }
}
