/*
 * InteractionIndex.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_INTERACTIONINDEX_H_
#define INCLUDE_INTERACTIONINDEX_H_

#include <memory>
#include <map>
#include <Interaction.h>

namespace vectorizer {

class InteractionIndex {

  std::map<std::string, std::shared_ptr<Interaction> > interaction_index;

  std::map<Vectorization*, std::vector< std::shared_ptr<Interaction> > > interaction_reverse_index;

  static InteractionIndex* instance;

  InteractionIndex() { }

public:

  ~InteractionIndex() { }

  static InteractionIndex& getInstance() {
    if (instance == nullptr) {
      instance = new InteractionIndex();
    }
    return *instance;
  }

  void set_index(const std::string& interaction_name, Vectorization* field_vectorizer) {
    auto p_interaction = interaction_index.find(interaction_name);
    if (p_interaction == interaction_index.end()) {
      interaction_index.emplace(std::make_pair(interaction_name, std::shared_ptr<Interaction>(new Interaction(interaction_name))));
    }
    p_interaction = interaction_index.find(interaction_name);

    p_interaction->second->incrSize();
    p_interaction->second->getFields().push_back(field_vectorizer);

    auto reverse_interaction = interaction_reverse_index[field_vectorizer];
    reverse_interaction.push_back(p_interaction->second);

  }

  std::map<std::string, std::shared_ptr<Interaction> >& getInteractionIndex() {
    return interaction_index;
  }

  std::map<Vectorization*, std::vector< std::shared_ptr<Interaction> > >& getInteractionReverseIndex() {
    return interaction_reverse_index;
  }
};

} /* namespace vectorizer */

#endif /* INCLUDE_INTERACTIONINDEX_H_ */
