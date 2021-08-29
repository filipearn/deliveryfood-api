package arn.filipe.fooddelivery.api.v1.model.input;

import arn.filipe.fooddelivery.core.validation.FileContentType;
import arn.filipe.fooddelivery.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PhotoProductInput {

    @ApiModelProperty(example = "Chorizzo.png (máx 500KB)", required = true)
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile file;

    @ApiModelProperty(example = "Product photo description", required = true)
    @NotBlank
    private String description;
}
