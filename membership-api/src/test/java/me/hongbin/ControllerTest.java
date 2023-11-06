package me.hongbin;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.hongbin.barcode.dto.BarcodeCreateRequest;
import me.hongbin.barcode.dto.BarcodeCreateResponse;
import me.hongbin.barcode.service.BarcodeService;
import me.hongbin.controller.BarcodeController;
import me.hongbin.controller.PointController;
import me.hongbin.generic.type.PageResponse;
import me.hongbin.partner.domain.Category;
import me.hongbin.point.domain.TransactionType;
import me.hongbin.point.dto.PointReadRequest;
import me.hongbin.point.dto.PointReadResponse;
import me.hongbin.point.dto.PointReadResponseItem;
import me.hongbin.point.dto.PointSaveRequest;
import me.hongbin.point.dto.PointUseRequest;
import me.hongbin.point.service.PointService;

@WebMvcTest(controllers = {BarcodeController.class, PointController.class})
@AutoConfigureRestDocs
public class ControllerTest {

    @MockBean
    private BarcodeService barcodeService;

    @MockBean
    private PointService pointService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("바코드를 생성할 수 있다.")
    @Test
    void name() throws Exception {
        var barcodeCreateRequest = new BarcodeCreateRequest(1L);
        var body = objectMapper.writeValueAsString(barcodeCreateRequest);
        var post = RestDocumentationRequestBuilders.post("/barcodes")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        when(barcodeService.create(any()))
            .thenReturn(new BarcodeCreateResponse("0123456789"));

        mockMvc.perform(post)
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document(
                "barcodes/create",
                "바코드 생성 API",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("userId").description("유저 식별자")
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("barcode").description("바코드")
                )
            ));
    }

    @DisplayName("포인트를 적립할 수 있다.")
    @Test
    void name2() throws Exception {
        var pointSaveRequest = new PointSaveRequest("01234656789", 1L, 5000L);
        var body = objectMapper.writeValueAsString(pointSaveRequest);

        var url = RestDocumentationRequestBuilders.post("/points").content(body)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(url)
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document(
                "points/save",
                "포인트 적립 API",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("barcode").description("바코드 정보"),
                    PayloadDocumentation.fieldWithPath("partnerId").description("파트너 식별자"),
                    PayloadDocumentation.fieldWithPath("point").description("적립할 포인트")
                )
            ));
    }

    @DisplayName("포인트를 사용할 수 있다.")
    @Test
    void name3() throws Exception {
        var request = new PointUseRequest("01234656789", 1L, 5000L);
        var body = objectMapper.writeValueAsString(request);

        var post = RestDocumentationRequestBuilders.post("/points/use")
            .content(body)
            .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(post)
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document(
                "points/use",
                "포인트 사용 API",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("barcode").description("바코드 정보"),
                    PayloadDocumentation.fieldWithPath("partnerId").description("파트너 식별자"),
                    PayloadDocumentation.fieldWithPath("point").description("적립할 포인트")
                )
            ));
    }

    @DisplayName("포인트사용내역을 조회할 수 있다.")
    @Test
    void name4() throws Exception {
        var pointReadRequest = new PointReadRequest(LocalDateTime.now(), LocalDateTime.now(), "0123456789");
        Map<String, String> params = objectMapper.convertValue(pointReadRequest, Map.class);
        var query = toMultipleMap(params);

        when(pointService.readAll(any(), any(), any(), any())).thenReturn(
            new PointReadResponse(
                new PageResponse(0, 20, 1),
                List.of(new PointReadResponseItem(LocalDateTime.now(), TransactionType.USE, Category.C, "네네치킨", 5000L)
                ))
        );

        var requestBuilder = RestDocumentationRequestBuilders.get("/points")
            .queryParams(query);

        mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(MockMvcRestDocumentationWrapper.document(
                "points/readAll",
                "포인트 사용내역 API",
                RequestDocumentation.queryParameters(
                    RequestDocumentation.parameterWithName("startDateTime").description("조회 시작일"),
                    RequestDocumentation.parameterWithName("endDateTime").description("조회 종료일"),
                    RequestDocumentation.parameterWithName("barcode").description("바코드"),
                    RequestDocumentation.parameterWithName("page").optional().description("요청 페이지"),
                    RequestDocumentation.parameterWithName("size").optional().description("요청 사이즈")
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("page").description("page"),
                    PayloadDocumentation.fieldWithPath("page.page").description("요청 페이지 (0부터 시작)"),
                    PayloadDocumentation.fieldWithPath("page.size").description("요청 사이즈"),
                    PayloadDocumentation.fieldWithPath("page.totalPage").description("총 페이지"),

                    PayloadDocumentation.fieldWithPath("items").description("items"),
                    PayloadDocumentation.fieldWithPath("items[].approvedAt").attributes(datetimeFormat())
                        .description("승인일자"),
                    PayloadDocumentation.fieldWithPath("items[].type").description("적립/사용 타입"),
                    PayloadDocumentation.fieldWithPath("items[].category").description("가맹점 업종"),
                    PayloadDocumentation.fieldWithPath("items[].partnerName").description("가맹점 이름"),
                    PayloadDocumentation.fieldWithPath("items[].amount").description("금액")
                )
            ));
    }

    private MultiValueMap<String, String> toMultipleMap(Map<String, String> prams) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        for (Map.Entry<String, String> entry : prams.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }

            multiValueMap.put(entry.getKey(), List.of(entry.getValue()));
        }

        return multiValueMap;
    }

    private Attributes.Attribute datetimeFormat() {
        return Attributes.key("format").value("yyyy-MM-dd'T'HH:mm:ss");
    }
}
