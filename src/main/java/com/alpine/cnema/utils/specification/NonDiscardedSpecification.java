package com.alpine.cnema.utils.specification;

import com.alpine.cnema.model.Auditable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;

public class NonDiscardedSpecification {
    public static <T extends Auditable> Specification<T> notDiscarded() {
        return (root, query, cb) -> cb.isNull(root.get("discardedAt"));
    }
    public static <T extends Auditable> Specification<T> notExpired() {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startTime"),LocalDateTime.now());
    }
}