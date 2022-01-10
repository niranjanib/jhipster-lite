package tech.jhipster.lite.generator.server.springboot.user.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaAuditEntity;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUser;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.assertFilesSqlJavaUserAuthority;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootUserResourceIT {

  @Autowired
  MockMvc mockMvc;

  @Test
  void shouldAddUserPostgresql() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    if (projectDTO == null) {
      throw new GeneratorException("Error when reading file");
    }
    projectDTO.folder(FileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/user/postgresql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, "postgresql");
    assertFilesSqlJavaUserAuthority(project, "postgresql");
    assertFilesSqlJavaAuditEntity(project, "postgresql");
  }
}
