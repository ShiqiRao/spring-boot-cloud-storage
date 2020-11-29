package com.example.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
class CloudStorageApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        //spring security测试相关配置
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin")
    public void testUpload() throws Exception {
        //测试文件上传
        MockMultipartFile file = new MockMultipartFile("fileUpload",
                "filename.txt", "text/plain", "some xml".getBytes());
        mvc.perform(multipart("/files").file(file))
                //期望的结果为转发，并且转发结果是"redirect:/result?success"
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/result?success"));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testAddNote() throws Exception {
        //测试添加笔记
        mvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("noteTitle", "title")
                .param("noteDescription", "description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/result?success"));
    }

}
