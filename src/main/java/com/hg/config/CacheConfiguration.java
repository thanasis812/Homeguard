package com.hg.config;

import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.hg.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.hg.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.hg.domain.Authority.class.getName());
            createCache(cm, com.hg.domain.Tenant.class.getName());
            createCache(cm, com.hg.domain.Tenant.class.getName() + ".propertyPreferences");
            createCache(cm, com.hg.domain.Tenant.class.getName() + ".apartmentReviews");
            createCache(cm, com.hg.domain.Tenant.class.getName() + ".rentedPropertysAgreements");
            createCache(cm, com.hg.domain.LandLord.class.getName());
            createCache(cm, com.hg.domain.LandLord.class.getName() + ".tenantReviews");
            createCache(cm, com.hg.domain.LandLord.class.getName() + ".rentalAgreements");
            createCache(cm, com.hg.domain.Location.class.getName());
            createCache(cm, com.hg.domain.Property.class.getName());
            createCache(cm, com.hg.domain.Property.class.getName() + ".rentals");
            createCache(cm, com.hg.domain.Property.class.getName() + ".houseCharacteristics");
            createCache(cm, com.hg.domain.Property.class.getName() + ".reviews");
            createCache(cm, com.hg.domain.Property.class.getName() + ".propertysPhotos");
            createCache(cm, com.hg.domain.TenantPropertyPreferences.class.getName());
            createCache(cm, com.hg.domain.RentalAgreement.class.getName());
            createCache(cm, com.hg.domain.RentalAgreement.class.getName() + ".payments");
            createCache(cm, com.hg.domain.HouseCharacteristics.class.getName());
            createCache(cm, com.hg.domain.Review.class.getName());
            createCache(cm, com.hg.domain.Review.class.getName() + ".images");
            createCache(cm, com.hg.domain.Payment.class.getName());
            createCache(cm, com.hg.domain.Image.class.getName());
            createCache(cm, com.hg.domain.LandLord.class.getName() + ".propertys");
            createCache(cm, com.hg.domain.Property.class.getName() + ".applications");
            createCache(cm, com.hg.domain.ApplicationRequest.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
