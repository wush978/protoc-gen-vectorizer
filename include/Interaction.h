/*
 * Interaction.h
 *
 *  Created on: 2016/11/17
 *      Author: gmobi-wush
 */

#ifndef INCLUDE_INTERACTION_H_
#define INCLUDE_INTERACTION_H_

#include <string>
#include <map>
#include <vector>

namespace vectorizer {

class Vectorization;

class Interaction {

  std::string name;

  std::vector<Vectorization*> fields;

public:

  Interaction(const std::string& _name) : name(_name), fields() { }

  ~Interaction() { }

  std::vector<Vectorization*>& getFields() {
    return fields;
  }

  const std::string& getName() const {
    return name;
  }

};

} /* namespace vectorizer */

#endif /* INCLUDE_INTERACTION_H_ */
