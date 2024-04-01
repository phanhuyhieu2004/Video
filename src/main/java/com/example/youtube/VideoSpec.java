package com.example.youtube;

import com.example.youtube.model.Video;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

//định nghĩa các điều kiện (specifications) dùng để lọc dữ liệu hoặc thực hiện các phương thức tương tự trong các tác vụ khác nhau.
public class VideoSpec implements Specification<Video> {
    private VideoRequest videoRequest;

    public VideoSpec(VideoRequest videoRequest) {
        this.videoRequest = videoRequest;
    }
//
//    @Override
//    public Specification<Video> and(Specification<Video> other) {
//        return Specification.super.and(other);
//    }
//
//    @Override
//    public Specification<Video> or(Specification<Video> other) {
//        return Specification.super.or(other);
//    }

    @Override
//    các điều kiện (predicates)
    //    đối tượng gốc của câu truy vấn, thường được sử dụng để truy cập các trường của đối tượng cơ sở dữ liệu
//xây dựng các điều kiện truy vấn.
    public Predicate toPredicate(Root<Video> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (videoRequest.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + videoRequest.getTitle() + "%"));
        }
        if (videoRequest.getUpload_date() != null) {
            predicates.add(criteriaBuilder.like(root.get("upload_date"), "%" + videoRequest.getUpload_date() + "%"));
        }
        if (videoRequest.getCategory() != null) {
            predicates.add(criteriaBuilder.equal(root.join("category").get("id"), videoRequest.getCategory()));
        }
//        kết hợp tất cả các điều kiện trong danh sách predicates với nhau bằng toán tử AND
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        toArray(T[] a) là một phương thức của lớp ArrayList, nó chuyển đổi một danh sách thành một mảng.
//        Trong trường hợp này, new Predicate[0] tạo ra một mảng rỗng của các đối tượng Predicate. Làm như vậy không yêu cầu phải chỉ định kích thước của mảng vì kích thước của mảng sẽ được tự động điều chỉnh bởi ArrayList để phù hợp với số lượng phần tử hiện tại trong danh sách.
//                Kết quả cuối cùng là một mảng các đối tượng Predicate chứa tất cả các phần tử từ danh sách predicates.
    }
}
