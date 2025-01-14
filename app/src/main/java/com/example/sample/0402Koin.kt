package com.example.sample

import androidx.lifecycle.ViewModel

// our class that uses a dependency
//class SampleViewModel: ViewModel() {
//    val repository: SampleRepository = SampleRepository()
//
//    init {
//        repository.doSomething()
//    }
//}

// our dependency
interface SampleRepository {
    fun doSomething()
}

class DefaultSampleRepository() : SampleRepository {
    override fun doSomething() {}
}

class FakeSampleRepository(): SampleRepository {
    override fun doSomething() {
        TODO("Not yet implemented")
    }
}

class SampleViewModel(repository: SampleRepository): ViewModel() {
    init {
        repository.doSomething()
    }
}

val viewModel = SampleViewModel(DefaultSampleRepository())
val viewModelInTest = SampleViewModel(FakeSampleRepository())
