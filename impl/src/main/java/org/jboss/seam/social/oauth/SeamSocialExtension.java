/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.social.oauth;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import org.jboss.logging.Logger;

/**
 * @author antoine
 * 
 */
@ApplicationScoped
public class SeamSocialExtension implements Extension {

    private Set<String> servicesNames = new HashSet<String>();
    private static final Logger log = Logger.getLogger(SeamSocialExtension.class);
    

    public void processBeans(@Observes ProcessBean<OAuthService> pbean, BeanManager beanManager)
    {
        
        log.info("*** Extension is in Process Bean ***");
        Annotated anno= pbean.getAnnotated();
        if (anno.isAnnotationPresent(RelatedTo.class))
        {
            String name=anno.getAnnotation(RelatedTo.class).value();
            log.info("=== Found service " + name + "  ===");
            servicesNames.add(anno.getAnnotation(RelatedTo.class).value());
        }
    }

    @Produces
    public Set<String> getSocialRelated() {
        return servicesNames;
    }

}
