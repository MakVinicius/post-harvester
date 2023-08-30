package uol.compass.postservice.dto;

public record AllPostsDTO<T> (
        Integer pageNumber,
        Integer pageSize,
        Integer numberOfPages,
        Long totalElements,
        Boolean isLast,
        T data
) {
}
