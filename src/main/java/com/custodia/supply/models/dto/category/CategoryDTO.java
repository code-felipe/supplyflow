package com.custodia.supply.models.dto.category;

public class CategoryDTO {

    private Long id;
    private String name;        // TISSUE, LINER, SOAP, BAG, TOWEL…
    private String description;

    private CategoryDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }
    
    public CategoryDTO(){
    	
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public CategoryDTO build() {
            return new CategoryDTO(this);
        }
    }

    // Getters (en DTO normalmente no necesitas setters)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
