/*
 * Copyright 2016 Wush Wu
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 */

#ifndef SRC_VECTORIZATION_H_
#define SRC_VECTORIZATION_H_

#include <memory>

namespace vectorizer {

class Vectorization {

private:

  std::shared_ptr<Vectorization> pInner;

public:

  Vectorization() : pInner(nullptr) { }

  Vectorization(const Vectorization& src) : pInner(src.pInner) { }

  Vectorization(Vectorization* inner) : pInner(inner) { };

  virtual ~Vectorization() { }

  virtual void generate(std::stringstream& out) {
    if (pInner.get() != nullptr) {
      pInner->generate(out);
    }
  }

  std::shared_ptr<Vectorization>& getInner() {
    return pInner;
  }

};

} /* namespace vectorizer */

#endif /* SRC_VECTORIZATION_H_ */
