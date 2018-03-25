package Bradibarus;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;

import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebApiTest {
    private MockMvc mockMvc;

    private UUID validId;
    private UUID invalidId;
    private CharSequence timeChars = "2018-03-25T18:26:38.818459+03:00";
    private final ZonedDateTime date = ZonedDateTime.parse(timeChars);
    private final String dataKey = "key";
    private final int dataValue = 1337;
    private final List<Data> data = new ArrayList<Data>() {{
        add(new Data(dataKey, dataValue));
    }};
    private final String jsonRequestBody = "{\n" +
            "\t\"Date\": \"2018-03-05T13:25:38.8184596+03:00\",\n" +
            "\t\"Data\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"Name\": \"Sample 1\",\n" +
            "\t\t\t\"Value\": 123123\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"Name\": \"Sample 2\",\n" +
            "\t\t\t\"Value\": 546546\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    private final String xmlRequestBody = "<Entry>\n" +
            "    <Date>2028-03-05T13:25:38.818459+03:00</Date>\n" +
            "    <Data>\n" +
            "        <Data>\n" +
            "            <Name>Sample 1</Name>\n" +
            "            <Value>123123</Value>\n" +
            "        </Data>\n" +
            "        <Data>\n" +
            "            <Name>Sample 2</Name>\n" +
            "            <Value>546546</Value>\n" +
            "        </Data>\n" +
            "    </Data>\n" +
            "</Entry>";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MediaType xmlContentType = new MediaType(MediaType.APPLICATION_XML.getType(),
            MediaType.APPLICATION_XML.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.validId = this.entryRepository.save(
                new Entry(this.date, this.data))
        .getId();

        this.invalidId = UUID.randomUUID(); //2^128, collision seems unlikely : )
    }

    @Test
    public void entryNotFound() throws Exception {
        mockMvc.perform(get("/api?id="+invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEntryJson() throws Exception {
        mockMvc.perform(get("/api?id="+validId)
                .accept(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))
                .andExpect(jsonPath("$.Date", is(timeChars)))
                .andExpect(jsonPath("$.Data[0].Name", is(dataKey)))
                .andExpect(jsonPath("$.Data[0].Value", is(dataValue)));

    }

    @Test
    public void getEntryXml() throws Exception {
        mockMvc.perform(get("/api?id="+validId)
                .accept(xmlContentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(xmlContentType))
                .andExpect(xpath("Entry/Date").string(String.valueOf(timeChars)))
                .andExpect(xpath("Entry/Data/Data/Name").string(dataKey))
                .andExpect(xpath("Entry/Data/Data/Value").string(String.valueOf(dataValue)));
    }

    @Test
    public void putEntryJson() throws Exception {
        mockMvc.perform(post("/api")
                .contentType(jsonContentType)
                .content(jsonRequestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void putEntryXml() throws Exception {
        mockMvc.perform(post("/api")
                .contentType(xmlContentType)
                .content(xmlRequestBody))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, jsonContentType, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
