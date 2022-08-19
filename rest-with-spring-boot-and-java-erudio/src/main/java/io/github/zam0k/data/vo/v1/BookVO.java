package io.github.zam0k.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "title", "launchDate", "price"})
public class BookVO extends RepresentationModel<BookVO> {
    @Mapping("id")
    @JsonProperty("id")
    private Long key;
    private String author;
    private LocalDate launchDate;
    private BigDecimal price;
    private String title;

    public BookVO() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVO bookVO = (BookVO) o;
        return getKey().equals(bookVO.getKey())
                && getAuthor().equals(bookVO.getAuthor())
                && getLaunchDate().equals(bookVO.getLaunchDate())
                && getPrice().equals(bookVO.getPrice())
                && getTitle().equals(bookVO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(),
                getAuthor(),
                getLaunchDate(),
                getPrice(), getTitle());
    }
}
