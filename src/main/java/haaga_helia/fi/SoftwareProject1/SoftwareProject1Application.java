package haaga_helia.fi.SoftwareProject1;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import haaga_helia.fi.SoftwareProject1.domain.QuizRepository;
import haaga_helia.fi.SoftwareProject1.domain.CategoryRepository;
import haaga_helia.fi.SoftwareProject1.domain.Quiz;
import haaga_helia.fi.SoftwareProject1.domain.Category;
import haaga_helia.fi.SoftwareProject1.domain.AppUser;
import haaga_helia.fi.SoftwareProject1.domain.AppUserRepository;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;	

@SpringBootApplication
public class SoftwareProject1Application extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SoftwareProject1Application.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SoftwareProject1Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(QuizRepository qrepository, CategoryRepository crepository, AppUserRepository arepository) {
		return (args) -> {
			Category category1 = new Category("example", "example");
			crepository.save(category1);
			Quiz quiz1 = new Quiz("example", "something", "who-cares", false, category1);
			qrepository.save(quiz1);
			AppUser teacherUser = new AppUser("Teacher", AppUser.Role.TEACHER);
			AppUser studentUser = new AppUser("Student", AppUser.Role.STUDENT);
			arepository.save(teacherUser);
			arepository.save(studentUser);
		};
	}

}
