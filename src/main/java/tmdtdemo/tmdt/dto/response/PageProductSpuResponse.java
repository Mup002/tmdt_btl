package tmdtdemo.tmdt.dto.response;
import java.util.List;
public record PageProductSpuResponse(
        List<ProductSpuResponse> responses,
        Integer pageNumber,
        Integer pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}
