package dolphins.foundation

class ShadyException(className: String) : Exception(
    "The instance of this $className is shady and behaves not according to laws for the sake of performance."
)