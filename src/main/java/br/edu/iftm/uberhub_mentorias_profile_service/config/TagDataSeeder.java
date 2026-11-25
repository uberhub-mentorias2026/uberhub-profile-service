package br.edu.iftm.uberhub_mentorias_profile_service.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.TagRepository;

// @Arthur - Carga inicial de tags nas 9 categorias caso a coleção esteja vazia
@Component
@ConditionalOnProperty(name = "profile.seed-tags", havingValue = "true", matchIfMissing = true)
public class TagDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TagDataSeeder.class);

    private final TagRepository tagRepository;

    public TagDataSeeder(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            if (tagRepository.count() > 0) {
                return;
            }
            List<Tag> tagsIniciais = buildInitialTags();
            tagRepository.saveAll(tagsIniciais);
            log.info("Carga inicial concluída: {} tags inseridas no MongoDB.", tagsIniciais.size());
        } catch (Exception ex) {
            log.warn("Carga inicial de tags ignorada (banco indisponível no momento): {}", ex.getMessage());
        }
    }

    public static List<Tag> buildInitialTags() {
        return List.of(
                // 1. Desenvolvimento Backend
                tag("Java", "Desenvolvimento Backend", "Linguagem Java e ecossistema JVM"),
                tag("Spring Boot", "Desenvolvimento Backend", "APIs REST e microsserviços com Spring Boot"),
                tag("Node.js", "Desenvolvimento Backend", "Desenvolvimento backend com JavaScript/TypeScript"),
                tag("Python", "Desenvolvimento Backend", "APIs e serviços backend em Python"),
                tag("Arquitetura de Microsserviços", "Desenvolvimento Backend",
                        "Design e comunicação entre microsserviços"),
                // 2. Desenvolvimento Frontend & Mobile
                tag("React", "Desenvolvimento Frontend & Mobile", "Interfaces web modernas com React"),
                tag("React Native", "Desenvolvimento Frontend & Mobile", "Aplicativos móveis com React Native e Expo"),
                tag("TypeScript", "Desenvolvimento Frontend & Mobile", "Tipagem estática para projetos JavaScript"),
                tag("Flutter", "Desenvolvimento Frontend & Mobile", "Desenvolvimento mobile multiplataforma"),
                // 3. Banco de Dados
                tag("MongoDB", "Banco de Dados", "Modelagem e consultas em banco NoSQL orientado a documentos"),
                tag("PostgreSQL", "Banco de Dados", "Banco de dados relacional e otimização SQL"),
                // 4. Cloud & DevOps
                tag("Docker", "Cloud & DevOps", "Containerização de aplicações e Docker Compose"),
                tag("AWS", "Cloud & DevOps", "Arquitetura e serviços em nuvem Amazon Web Services"),
                tag("CI/CD & GitHub Actions", "Cloud & DevOps",
                        "Automação de pipelines de integração e entrega contínua"),
                // 5. Dados & IA
                tag("Ciência de Dados", "Dados & IA", "Análise exploratória e visualização de dados"),
                tag("Machine Learning", "Dados & IA", "Modelos preditivos e aprendizado de máquina"),
                tag("IA Generativa & LLMs", "Dados & IA", "Engenharia de prompt e integração com LLMs"),
                // 6. Segurança & Qualidade (QA)
                tag("Segurança de Aplicações", "Segurança & Qualidade (QA)",
                        "OWASP, autenticação JWT/OAuth2 e proteção de APIs"),
                tag("Testes Automatizados", "Segurança & Qualidade (QA)",
                        "Testes unitários e de integração (JUnit, Jest)"),
                // 7. Produto & Agilidade
                tag("Product Management", "Produto & Agilidade", "Descoberta de produto, roadmap e priorização"),
                tag("Scrum & Métodos Ágeis", "Produto & Agilidade", "Gestão de sprints e cerimônias ágeis"),
                // 8. UX/UI Design
                tag("UI Design & Figma", "UX/UI Design", "Prototipação de interfaces e Design Systems"),
                tag("UX Research", "UX/UI Design", "Pesquisa com usuários e testes de usabilidade"),
                // 9. Carreira & Inovação
                tag("Transição de Carreira", "Carreira & Inovação", "Currículo, LinkedIn, portfólio e entrevistas"),
                tag("Liderança Técnica", "Carreira & Inovação", "Mentoria de times, code review e decisão técnica"));
    }

    private static Tag tag(String name, String category, String description) {
        return Tag.builder()
                .name(name)
                .category(category)
                .description(description)
                .build();
    }
}